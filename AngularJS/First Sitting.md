Og ref: https://docs.angularjs.org/tutorial


This will tie together:

- AngularJS (1.x)
    
- DI
    
- Routing
    
- Components
    
- $http vs $resource
    
- Enterprise folder structure
    
- CommonJS wrapper style (office pattern)
    
- How it connects to a Spring backend
    

---

# AngularJS (1.x) Enterprise Quick Start

### With CommonJS-style Module Registration

This document explains a typical enterprise AngularJS 1.x structure using:

- Modular folder layout
    
- CommonJS-style component registration
    
- Dependency Injection (minification-safe)
    
- Routing
    
- Service layer abstraction
    
- REST backend integration (e.g., Spring)
    

---

# 1. High-Level Architecture

Frontend (AngularJS SPA)

Components  
→ Services  
→ $resource / $http  
→ Spring REST API  
→ Database

Angular handles:

- View rendering
    
- Routing
    
- Client-side state
    
- REST communication
    

Spring handles:

- Business logic
    
- Validation
    
- Persistence
    
- Security
    

---

# 2. Why Your Office Uses CommonJS Pattern

You mentioned writing components like:

```js
module.exports = function(ngModule) {
  ngModule.component(...)
};
```

This is a **CommonJS-style wrapper pattern**.

It is NOT native Angular syntax.

It is a pattern used when:

- Using Webpack / Browserify
    
- Keeping each feature isolated
    
- Avoiding global `angular.module(...)` calls everywhere
    

Instead of:

```js
angular.module('phoneDetail')
  .component(...)
```

Your company does:

```js
module.exports = function(ngModule) {
  ngModule.component(...)
};
```

This makes the file reusable and testable.

---

# 3. How CommonJS Style Works

## Without CommonJS (classic Angular)

```js
angular.module('phoneDetail')
  .component('phoneDetail', {...});
```

Problem:

- The file assumes the module already exists globally.
    

---

## With CommonJS Wrapper (enterprise style)

phone-detail.component.js

```js
module.exports = function(ngModule) {

  ngModule.component('phoneDetail', {
    templateUrl: 'phone-detail/phone-detail.template.html',
    controller: PhoneDetailController
  });

  PhoneDetailController.$inject = ['Phone', '$routeParams'];

  function PhoneDetailController(Phone, $routeParams) {
    var self = this;

    self.$onInit = function() {
      self.phone = Phone.get({
        phoneId: $routeParams.phoneId
      });
    };
  }
};
```

Now this file does not create a module.

It receives `ngModule` as a parameter.

---

# 4. Where ngModule Comes From

phone-detail.module.js

```js
var angular = require('angular');
var phoneDetailComponent = require('./phone-detail.component');

var moduleName = 'phoneDetail';

var ngModule = angular.module(moduleName, []);

phoneDetailComponent(ngModule);

module.exports = moduleName;
```

What happens here:

1. Create module
    
2. Pass module to component file
    
3. Component registers itself on that module
    
4. Export module name
    

This is very clean and enterprise-friendly.

---

# 5. Root App Module (Wiring Everything Together)

app.module.js

```js
var angular = require('angular');
require('angular-route');
require('angular-resource');

var phoneDetailModule = require('./phone-detail/phone-detail.module');
var phoneListModule = require('./phone-list/phone-list.module');
var coreModule = require('./core/core.module');

angular.module('phonecatApp', [
  'ngRoute',
  'ngResource',
  phoneDetailModule,
  phoneListModule,
  coreModule
]);
```

This ensures:

- Feature modules are independent
    
- Root module just composes them
    

---

# 6. Routing (Config Block)

app.config.js

```js
module.exports = function(ngModule) {

  ngModule.config(RouteConfig);

  RouteConfig.$inject = ['$routeProvider'];

  function RouteConfig($routeProvider) {

    $routeProvider
      .when('/phones', {
        template: '<phone-list></phone-list>'
      })
      .when('/phones/:phoneId', {
        template: '<phone-detail></phone-detail>'
      })
      .otherwise('/phones');
  }
};
```

Then in app.module.js:

```js
var appModule = angular.module('phonecatApp', [...]);

require('./app.config')(appModule);
```

---

# 7. Dependency Injection — Office Standard

You will see this in enterprise code:

Option 1: Inline array (min-safe)

```js
controller: ['Phone', '$routeParams', function(Phone, $routeParams) {}]
```

Option 2: $inject property (cleaner)

```js
PhoneDetailController.$inject = ['Phone', '$routeParams'];

function PhoneDetailController(Phone, $routeParams) {}
```

Most enterprise code prefers:

$inject style.

Cleaner and testable.

---

# 8. Service Layer Pattern (Core Module)

core/phone/phone.service.js

```js
module.exports = function(ngModule) {

  ngModule.factory('Phone', PhoneFactory);

  PhoneFactory.$inject = ['$resource'];

  function PhoneFactory($resource) {

    return $resource('/api/phones/:phoneId', {}, {
      update: {
        method: 'PUT'
      }
    });
  }
};
```

core/phone/phone.module.js

```js
var angular = require('angular');
var phoneService = require('./phone.service');

var moduleName = 'core.phone';

var ngModule = angular.module(moduleName, ['ngResource']);

phoneService(ngModule);

module.exports = moduleName;
```

This mirrors Spring REST:

GET /api/phones  
GET /api/phones/{id}  
PUT /api/phones/{id}  
POST /api/phones

---

# 9. $http vs $resource

$http:

- Low-level
    
- Manual endpoint handling
    
- More flexible
    

$resource:

- REST-oriented
    
- Cleaner
    
- Automatically maps to REST verbs
    

Enterprise apps often prefer:

- $resource for CRUD entities
    
- $http for special cases
    

---

# 10. Lifecycle in Components

Use $onInit instead of constructor logic.

```js
self.$onInit = function() {
   // initialization
};
```

Other hooks:

- $onChanges
    
- $onDestroy
    

These matter in large apps.

---

# 11. Complete Request Flow

User clicks link  
→ Route changes  
→ Component loads  
→ $routeParams provides ID  
→ Service (Phone) calls backend  
→ Spring returns JSON  
→ Angular binds response to view  
→ Digest cycle updates DOM

---

# 12. How This Relates to Spring

Angular Component ≈ Combination of Controller + JSP  
Angular Service ≈ Spring Service + RestTemplate  
$resource ≈ REST client abstraction  
DI in Angular ≈ Spring Dependency Injection  
Config block ≈ @Configuration

---

# 13. Why Enterprise Uses CommonJS Pattern

Benefits:

1. No global angular.module lookups
    
2. Clear module ownership
    
3. Better testability
    
4. Works with bundlers
    
5. Cleaner dependency graph
    
6. Explicit wiring
    

It makes AngularJS behave more like a modular backend project.

---

# 14. Mental Model for Your Office Codebase

If you see:

```js
module.exports = function(ngModule) {
   ngModule.component(...)
};
```

Think:

"This file registers something onto a module created elsewhere."

If you see:

```js
require('./xyz')(ngModule);
```

Think:

"This feature is being attached to the module."

---

# 15. Summary

You now have:

- Classic AngularJS structure
    
- Enterprise folder layout
    
- CommonJS-style registration pattern
    
- DI best practices
    
- Routing configuration
    
- Service abstraction
    
- REST integration model
    
- Lifecycle hooks
    
- $http vs $resource difference
    

This covers what you would typically encounter in a Java + AngularJS enterprise codebase.

If you want, next we can:

- Walk through a real Spring REST controller + AngularJS integration example
    
- Explain digest cycle and change detection deeply
    
- Explain how unit testing works in this CommonJS setup
    
- Compare this to modern Angular architecture