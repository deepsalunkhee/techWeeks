# AngularJS 1.x — Complete Study Notes
> **Context:** Enterprise codebase using CommonJS pattern (Webpack/Browserify), Spring REST backend.  
> **Purpose:** Revision reference from basics to advanced.  
> **Official Docs:** https://docs.angularjs.org/guide | **API Reference:** https://docs.angularjs.org/api

---

## Table of Contents

1. [What is AngularJS](#1-what-is-angularjs)
2. [MVC Architecture in AngularJS](#2-mvc-architecture-in-angularjs)
3. [Bootstrapping an AngularJS App](#3-bootstrapping-an-angularjs-app)
4. [Modules](#4-modules)
5. [CommonJS Pattern (Enterprise Style)](#5-commonjs-pattern-enterprise-style)
6. [Controllers](#6-controllers)
7. [Scope and $rootScope](#7-scope-and-rootscope)
8. [Dependency Injection (DI)](#8-dependency-injection-di)
9. [Services, Factories, Providers, Values, Constants](#9-services-factories-providers-values-constants)
10. [Directives](#10-directives)
11. [Data Binding](#11-data-binding)
12. [Filters](#12-filters)
13. [Routing (ngRoute)](#13-routing-ngroute)
14. [Components (1.5+)](#14-components-15)
15. [Lifecycle Hooks](#15-lifecycle-hooks)
16. [$http vs $resource](#16-http-vs-resource)
17. [Digest Cycle and $watch](#17-digest-cycle-and-watch)
18. [Scope Lifecycle](#18-scope-lifecycle)
19. [Expressions vs JavaScript](#19-expressions-vs-javascript)
20. [Performance Optimization](#20-performance-optimization)
21. [Logging with $log](#21-logging-with-log)
22. [$location Service](#22-location-service)
23. [Internationalization (i18n)](#23-internationalization-i18n)
24. [$ vs $$ Prefix](#24--vs--prefix)
25. [AngularJS vs Spring — Mental Model Mapping](#25-angularjs-vs-spring--mental-model-mapping)
26. [Quick Reference — Interview Topics](#26-quick-reference--interview-topics)

---

## 1. What is AngularJS

- An open-source, JavaScript-based **MVW (Model-View-Whatever) framework** developed by Google.
- Designed for building **large-scale, enterprise-level, dynamic Single Page Applications (SPAs)**.
- Extends HTML with custom attributes and elements (directives).
- Follows **two-way data binding**, meaning the model and view stay in sync automatically.
- Core design principle: **declarative UI + imperative logic**.
- Latest stable version: **v1.8.3**.

> 📖 https://docs.angularjs.org/guide/introduction

---

## 2. MVC Architecture in AngularJS

| Layer | Role | AngularJS Equivalent |
|---|---|---|
| **Model** | Application data | `$scope` properties |
| **View** | Rendered HTML | Templates / HTML with directives |
| **Controller** | Logic connecting model and view | `ng-controller` / component controller |

- AngularJS is sometimes called **MVW** because the "Whatever" acknowledges flexibility — you can apply service layers, MVVM, etc.
- The **`$scope`** object is the glue between View and Controller.

> 📖 https://docs.angularjs.org/guide/concepts

---

## 3. Bootstrapping an AngularJS App

Two ways to bootstrap:

**Auto Bootstrap** — AngularJS automatically bootstraps on `DOMContentLoaded` when it finds the `ng-app` directive in the HTML. It then:
1. Loads the module associated with `ng-app`.
2. Creates the `$injector`.
3. Creates `$compile` and `$rootScope`.
4. Compiles the DOM.

**Manual Bootstrap** — Using `angular.bootstrap(element, ['moduleName'])`. Used when you need to control when bootstrapping happens (e.g., after async operations).

> 📖 https://docs.angularjs.org/guide/bootstrap

---

## 4. Modules

- A **module** is a container for the different parts of an application — controllers, services, filters, directives, config, routes.
- Think of it as the equivalent of Java's `main()` method — it is the entry point that wires everything together.
- Created with `angular.module('name', [dependencies])`.
- Retrieved (not recreated) with `angular.module('name')` (no second argument).

**Key Rule:** Passing the dependency array `[]` **creates** the module. Omitting it **retrieves** it.

**Module types in an enterprise app:**
- **Root/App module** — wires everything together.
- **Feature modules** — isolated per feature (e.g., `phoneDetail`, `phoneList`).
- **Core/shared module** — shared services, utilities.

> 📖 https://docs.angularjs.org/guide/module

---

## 5. CommonJS Pattern (Enterprise Style)

This is the pattern your office uses. It is **not** native Angular syntax — it is a **Webpack/Browserify pattern** for modular code organization.

### Why it exists:
- Avoids global `angular.module(...)` calls scattered across files.
- Each file is isolated and only knows about what it receives.
- Makes components individually testable.
- Enables clean dependency graphs.
- Plays well with bundlers (Webpack, Browserify).

### The pattern — three-part structure:

**Part 1 — The component/service file** receives `ngModule` as a parameter and registers onto it:
```js
module.exports = function(ngModule) {
  ngModule.component('myComponent', { ... });
};
```

**Part 2 — The feature module file** creates the Angular module and passes it to the component:
```js
var angular = require('angular');
var myComponent = require('./my.component');

var ngModule = angular.module('myFeature', []);
myComponent(ngModule);

module.exports = 'myFeature'; // exports the module NAME (string)
```

**Part 3 — The root app module** collects all feature module names as dependencies:
```js
var angular = require('angular');
var myFeature = require('./my-feature/my-feature.module');

angular.module('app', [myFeature]);
```

### Mental model:
- `module.exports = function(ngModule) {...}` → "I register something onto a module given to me."
- `require('./xyz')(ngModule)` → "Attach this feature to my module."
- The feature module file is the **owner** of the Angular module.

---

## 6. Controllers

- Controllers are **JavaScript constructor functions** bound to a scope.
- Their job: initialize the `$scope` model and define behavior.
- They should **not** handle DOM manipulation or data fetching directly — that is the service's job.
- In the **component style** (preferred in 1.5+), you use `this` (controllerAs) instead of `$scope`.

### Minification-safe DI styles:

**Inline array annotation:**
```js
controller: ['$scope', 'MyService', function($scope, MyService) { }]
```

**`$inject` property (enterprise preferred — cleaner, more readable):**
```js
MyController.$inject = ['$scope', 'MyService'];
function MyController($scope, MyService) { }
```

> 📖 https://docs.angularjs.org/guide/controller

---

## 7. Scope and $rootScope

### $scope
- A **plain JavaScript object** that acts as the glue between controller and view.
- Holds model data and methods exposed to the template.
- Scopes are **hierarchical** — they mimic the DOM structure.
- Child scopes **prototypically inherit** from parent scopes.

### $rootScope
- The **single root scope** of the entire application — created on the element with `ng-app`.
- Available application-wide.
- All other scopes are its descendants.
- Avoid polluting `$rootScope` with too many properties — it becomes hard to maintain.

### Scope hierarchy rules:
- Child can access parent's properties (prototypal inheritance).
- Parent **cannot** access child's properties.
- Isolated scope (used in directives) breaks the prototypal chain intentionally.

### Scope characteristics:
1. Provides context for evaluating AngularJS expressions.
2. Observes model changes via `$watch`.
3. Propagates model changes via `$apply`.
4. Inherits parent properties.
5. Can be nested to isolate directive scopes.

> 📖 https://docs.angularjs.org/guide/scope

---

## 8. Dependency Injection (DI)

- DI is AngularJS's **core design pattern** — it was built specifically to demonstrate and promote DI.
- Instead of a component creating its own dependencies, the **injector creates them and passes them in**.
- The **`$injector`** is responsible for creating and wiring all AngularJS objects.

### Why DI matters:
- Decouples components from their dependencies.
- Makes components reusable, maintainable, and testable.
- You can swap out implementations without touching consumers.

### How AngularJS resolves DI:
The injector reads the **function parameter names** to determine what to inject. This breaks during **minification** (parameters get renamed to single letters). That is why annotation is required.

### Three annotation styles:
1. **Implicit (not minification-safe):** `function MyCtrl($scope) {}`
2. **Inline array (safe):** `['$scope', function($scope) {}]`
3. **`$inject` property (safe, enterprise preferred):** `MyCtrl.$inject = ['$scope']`

### What can be injected:
- Services (`$http`, `$q`, `$timeout`, custom services)
- Factories
- Providers
- Values
- Constants

> 📖 https://docs.angularjs.org/guide/di

---

## 9. Services, Factories, Providers, Values, Constants

All are **singleton objects** managed by AngularJS's injector. The difference is in how they are defined and what flexibility they offer.

| Recipe | Singleton | Configurable | Returns | Use When |
|---|---|---|---|---|
| **Value** | Yes | No | Any value | Simple values/objects |
| **Constant** | Yes | No (available in config) | Any value | Config-time constants |
| **Factory** | Yes | No | Return value of function | Most common, flexible |
| **Service** | Yes | No | `new` instance of function | When using class-style |
| **Provider** | Yes | **Yes** (in config phase) | `$get` return value | When you need config-time setup |

### Key distinctions:
- **Factory** — you return what you want injected. Most commonly used.
- **Service** — AngularJS calls `new` on your function. `this` properties become the injectable.
- **Provider** — the most verbose but most powerful. The only recipe available during the `config` phase. All other recipes are syntactic sugar over Provider.
- **`$resource`** is itself a factory that wraps `$http` for REST interactions.

> 📖 https://docs.angularjs.org/guide/services  
> 📖 https://docs.angularjs.org/guide/providers

---

## 10. Directives

- Directives are **markers on DOM elements** (attributes, element names, class names, comments) that tell AngularJS to attach a behavior or transform the DOM.
- They are the way AngularJS extends HTML vocabulary.

### Built-in directives (key ones):

| Directive | Purpose |
|---|---|
| `ng-app` | Bootstraps the application |
| `ng-controller` | Attaches a controller to a DOM element |
| `ng-model` | Two-way binds input to scope |
| `ng-bind` | One-way binds scope to element text |
| `ng-repeat` | Iterates over a collection |
| `ng-if` | Removes/recreates DOM element based on condition |
| `ng-show` / `ng-hide` | Shows/hides element (DOM stays, CSS toggles) |
| `ng-click` | Attaches click event handler |
| `ng-dblclick` | Attaches double-click event handler |
| `ng-include` | Embeds external HTML templates |
| `ng-class` | Dynamically sets CSS classes |
| `ng-options` | Generates `<option>` elements for `<select>` (requires `ng-model`) |
| `ng-init` | Initializes scope variables inline |

### `ng-if` vs `ng-show`:
- `ng-if` — **removes** the element from DOM when false. Scope is **destroyed**.
- `ng-show` — **keeps** the element in DOM, applies `ng-hide` CSS class. Scope **persists**.

### `ng-repeat` and `track by`:
- `ng-repeat` tracks DOM nodes by object identity. When items update, it destroys and recreates nodes.
- `track by obj.id` tells it to track by a unique property instead — avoids unnecessary DOM recreation, improves performance significantly.

### Custom Directives — `restrict` values:
- `'E'` — Element name: `<my-directive></my-directive>`
- `'A'` — Attribute: `<div my-directive></div>`
- `'C'` — Class: `<div class="my-directive"></div>`
- `'M'` — Comment: `<!-- directive: my-directive -->`

### Directive scope options:
- **No `scope` key** — uses parent scope (shared).
- **`scope: true`** — creates a new child scope (prototypally inherits from parent).
- **`scope: {}`** — **isolated scope** — no inheritance. Best for reusable components.

### Compile vs Link:
- **Compile** — traverses the DOM, finds directives, returns link functions. Runs once per template. No scope access.
- **Link** — combines the directive with the scope. Runs per instance. Where DOM manipulation and event listeners go.

> 📖 https://docs.angularjs.org/guide/directive

---

## 11. Data Binding

Data binding is the **automatic synchronization** between model (`$scope`) and view (template).

### One-way binding (model → view):
- Uses `ng-bind` directive or `{{ expression }}` interpolation.
- View reflects model changes but view changes don't affect model.

### Two-way binding (model ↔ view):
- Uses `ng-model` directive.
- Changes in the view automatically update the model and vice versa.

### Interpolation:
- `{{ expression }}` — AngularJS evaluates the expression against the current scope and renders the result.
- Internally uses the `$interpolate` service, which registers watches for updates.

> 📖 https://docs.angularjs.org/guide/databinding

---

## 12. Filters

- Filters **format data** in templates without modifying the underlying model value.
- Applied using the pipe character: `{{ expression | filterName:arg }}`.
- Can also be used in controllers/services by injecting the filter.

### Built-in filters:

| Filter | Purpose |
|---|---|
| `currency` | Formats number as currency |
| `date` | Formats date |
| `filter` | Selects subset of array |
| `json` | JSON stringify for display |
| `limitTo` | Limits array/string to N items |
| `lowercase` / `uppercase` | Case conversion |
| `number` | Formats number with decimal places |
| `orderBy` | Re-orders array items by expression |

### Custom filters:
- Registered via `ngModule.filter('filterName', function() { return function(input) { return ...; }; })`.

> 📖 https://docs.angularjs.org/guide/filter

---

## 13. Routing (ngRoute)

- Routing enables SPA behavior — different views are loaded for different URLs without full page reloads.
- Provided by the **`ngRoute`** module (separate script, must be included and declared as dependency).
- The core service is **`$routeProvider`** (configured in a `config` block).
- **`$routeParams`** — injected into controllers to access URL parameters (e.g., `:phoneId`).
- **`$location`** — service that parses and provides access to the current URL; changes to `$location` are reflected in the browser address bar and vice versa.

### How routing works:
1. User navigates to a URL (or clicks a link).
2. `$routeProvider` matches the URL against defined routes.
3. The matched template is injected into `<ng-view>`.
4. The matched controller is instantiated with the correct scope.

### Route configuration pattern (CommonJS style):
```js
module.exports = function(ngModule) {
  ngModule.config(RouteConfig);
  RouteConfig.$inject = ['$routeProvider'];
  function RouteConfig($routeProvider) {
    $routeProvider
      .when('/phones', { template: '<phone-list></phone-list>' })
      .when('/phones/:phoneId', { template: '<phone-detail></phone-detail>' })
      .otherwise('/phones');
  }
};
```

### `$location` service:
- Wraps `window.location` and exposes it to AngularJS.
- Provides methods: `.path()`, `.search()`, `.hash()`, `.url()`.
- Changes to `$location` trigger route changes.

> 📖 https://docs.angularjs.org/guide/routing  
> 📖 https://docs.angularjs.org/api/ngRoute/service/$routeProvider

---

## 14. Components (1.5+)

- Introduced in AngularJS 1.5 as a **simplified, opinionated directive** for building UI pieces.
- Encourage a component-based architecture similar to modern Angular/React.
- Always have **isolated scope**.
- Always use **`controllerAs: '$ctrl'`** by default (so `this` inside controller maps to `$ctrl` in template).
- Bindings are declared explicitly via `bindings: {}`.

### Bindings types:
| Symbol | Meaning |
|---|---|
| `<` | One-way binding (input from parent) |
| `@` | String/attribute binding |
| `&` | Expression/callback binding (output to parent) |
| `=` | Two-way binding (legacy, avoid in new code) |

### Why components over directives for UI:
- Less boilerplate.
- Lifecycle hooks built-in.
- Naturally maps to Angular 2+ concepts (easier migration path).
- Always isolated scope means predictable behavior.

> 📖 https://docs.angularjs.org/guide/component

---

## 15. Lifecycle Hooks

Available in **component controllers** (1.5+):

| Hook | When it runs |
|---|---|
| `$onInit()` | After bindings are set. Use for initialization logic. Replaces constructor-style setup. |
| `$onChanges(changesObj)` | When one-way bound inputs change. `changesObj` has `currentValue` and `previousValue`. |
| `$doCheck()` | Every digest cycle — use for custom change detection. |
| `$onDestroy()` | Before the component is destroyed. Use for cleanup (deregister watchers, cancel timers). |
| `$postLink()` | After component's view and child views are linked. Use for DOM manipulation. |

### Best practice:
- Always put initialization in `$onInit`, never in the constructor function directly.
- Always clean up in `$onDestroy` to prevent memory leaks.

> 📖 https://docs.angularjs.org/guide/component#component-based-application-architecture

---

## 16. $http vs $resource

### `$http`
- Low-level service for making XMLHttpRequests (AJAX calls).
- Returns a **promise** (`.then(successFn, errorFn)`).
- Full control over every request.
- Use for: non-RESTful endpoints, file uploads, special headers, custom logic.

**POST syntax:**
```js
$http({
  method: 'POST',
  url: '/api/endpoint',
  data: payload,
  headers: { 'Content-Type': 'application/json' }
}).then(function(response) { }, function(error) { });
```

### `$resource` (from `ngResource` module)
- Higher-level abstraction built on top of `$http`.
- Designed specifically for **RESTful APIs**.
- Automatically maps HTTP verbs to CRUD operations.
- Returns **resource objects** with methods like `.get()`, `.query()`, `.save()`, `.delete()`.

**Default method mappings:**
| Method | HTTP Verb |
|---|---|
| `.get({id})` | `GET /resource/:id` |
| `.query()` | `GET /resource` (returns array) |
| `.save(data)` | `POST /resource` |
| `.delete({id})` | `DELETE /resource/:id` |
| Custom `update` | `PUT /resource/:id` (must define manually) |

**Enterprise preference:**
- Use `$resource` for standard CRUD entities (matches Spring REST controllers).
- Use `$http` for special-case endpoints, authentication calls, or anything non-standard.

> 📖 https://docs.angularjs.org/api/ng/service/$http  
> 📖 https://docs.angularjs.org/api/ngResource/service/$resource

---

## 17. Digest Cycle and $watch

### The Digest Cycle
- The engine behind AngularJS's two-way data binding.
- A loop that checks all **watchers** registered on scopes.
- Compares the **current value** of a watched expression against its **last known value**.
- If a change is detected, the corresponding listener function is called, and the cycle runs again.
- Continues until no more changes are detected (**stable state**) or hits a maximum of 10 iterations (throws `$digest: infdig` error if exceeded).

### What triggers the digest cycle:
- User events handled by AngularJS directives (`ng-click`, `ng-change`, etc.).
- `$http` / `$resource` responses.
- `$timeout` / `$interval` completions.
- Calling `$scope.$apply()` manually (for code outside AngularJS context, e.g., jQuery, native JS events).

### `$watch`
- Registers a watcher on a scope expression.
- Called every digest cycle to detect changes.
- Returns a **deregister function** — call it to stop watching (important for cleanup).

```js
var deregister = $scope.$watch('myVar', function(newVal, oldVal) { });
// To stop watching:
deregister();
```

### `$apply`
- Used to bring changes made **outside** AngularJS context into the digest cycle.
- Internally calls `$rootScope.$digest()`.
- If you call `$apply` inside an existing digest, it throws an error — use `$timeout` with 0ms delay instead as a safe alternative.

### `$watchCollection`
- Watches shallow changes in arrays and objects (not deep).
- More performant than a deep `$watch`.

> 📖 https://docs.angularjs.org/guide/scope#scope-life-cycle

---

## 18. Scope Lifecycle

The five phases of a scope's life:

1. **Creation** — `$rootScope` created by `$injector` during bootstrap. Child scopes created during template linking when directives that create scopes are encountered.
2. **Watcher Registration** — Directives register `$watch` expressions during template compilation/linking.
3. **Model Mutation** — Application code changes model properties. Must happen within `$scope.$apply()` to be observed (AngularJS built-in directives/services do this automatically).
4. **Mutation Observation** — The digest cycle runs, `$watch` listeners are called for changed values.
5. **Scope Destruction** — `$scope.$destroy()` is called when the scope is no longer needed (e.g., when `ng-if` becomes false, or a route changes). Cleans up watchers and frees memory.

> 📖 https://docs.angularjs.org/guide/scope#scope-life-cycle

---

## 19. Expressions vs JavaScript

| | AngularJS Expressions | JavaScript Expressions |
|---|---|---|
| **Evaluated against** | Current `$scope` object | Global `window` object |
| **Error handling** | Forgiving — returns `null` or `undefined` on errors | Throws errors |
| **Loops/conditionals** | Not supported inside `{{ }}` | Fully supported |
| **Filters** | Supported via pipe `|` | Not supported natively |
| **Context** | Sandboxed — no access to global scope | Full access |

> 📖 https://docs.angularjs.org/guide/expression

---

## 20. Performance Optimization

### Official recommendations:

**1. Enable Strict DI Mode**
- Forces all injectable functions to be explicitly annotated.
- Throws an error immediately if implicit annotation is used.
- Useful during development to catch minification issues early.
- Applied via `ng-strict-di` attribute on the `ng-app` element.

**2. Disable Debug Data in Production**
- AngularJS attaches debug data (scope references) to DOM elements for tooling like Batarang.
- This is expensive in production.
- Disable via `$compileProvider.debugInfoEnabled(false)` in the config block.

### Additional techniques:

| Technique | Why it helps |
|---|---|
| **One-time binding (`::`)** | `{{ ::value }}` — binds once, then deregisters the watcher. Reduces watcher count. |
| **Minimize `$watch` count** | Every watcher runs every digest cycle. Fewer watchers = faster cycles. |
| **`track by` in `ng-repeat`** | Avoids destroying and recreating DOM nodes unnecessarily. |
| **Pagination / infinite scroll** | Reduces the number of DOM nodes and watchers at any given time. |
| **`$cacheFactory`** | Cache results of expensive repeated calculations. |
| **`$httpProvider.useApplyAsync(true)`** | Batches HTTP responses into a single digest cycle. |

> 📖 https://docs.angularjs.org/guide/production

---

## 21. Logging with $log

- `$log` is AngularJS's built-in logging service. Wraps `console` methods.
- Allows log output to be mocked during testing.

| Method | Purpose |
|---|---|
| `$log.log(msg)` | General log |
| `$log.info(msg)` | Informational message |
| `$log.warn(msg)` | Warning |
| `$log.error(msg)` | Error |
| `$log.debug(msg)` | Debug message |

> 📖 https://docs.angularjs.org/api/ng/service/$log

---

## 22. $location Service

- Wraps the browser's `window.location` and integrates it with AngularJS's digest cycle.
- Changes to `$location` are reflected in the browser address bar.
- Changes in the address bar are reflected in `$location`.

### Key methods:
- `.path()` — get/set the path.
- `.search()` — get/set query string parameters.
- `.hash()` — get/set the hash fragment.
- `.url()` — get/set the full URL path + search + hash.

### HTML5 mode:
- By default, AngularJS uses hash-based URLs (`#!/phones`).
- Enabling HTML5 mode via `$locationProvider.html5Mode(true)` gives clean URLs (`/phones`), but requires server-side configuration.

> 📖 https://docs.angularjs.org/api/ng/service/$location

---

## 23. Internationalization (i18n)

- AngularJS has built-in support for locale-specific formatting via the `$locale` service.
- For full i18n (translations), the community module **`angular-translate`** is the standard solution.
- `angular-translate` provides filters, directives, and async loading of translation files.
- Supports **pluralization** via MessageFormat.

> 📖 https://docs.angularjs.org/guide/i18n  
> 📖 https://angular-translate.github.io

---

## 24. $ vs $$ Prefix

| Prefix | Meaning | Examples |
|---|---|---|
| `$` | Public AngularJS built-in variable, service, or method | `$scope`, `$http`, `$watch`, `$routeProvider` |
| `$$` | **Private** AngularJS internal — **do not use in application code** | `$$watchers`, `$$childScope`, `$$observers` |

- `$$` properties are implementation details of AngularJS internals.
- Accessing or modifying `$$` properties is unsupported and can break across versions.

---

## 25. AngularJS vs Spring — Mental Model Mapping

| AngularJS | Spring |
|---|---|
| Component (controller + template) | Controller + JSP/Thymeleaf view |
| Service / Factory | `@Service` class |
| `$resource` / `$http` | `RestTemplate` / `WebClient` |
| Dependency Injection (`$inject`) | `@Autowired` |
| Config block (`ngModule.config`) | `@Configuration` class |
| `$routeProvider` route definition | `@RequestMapping` / `@GetMapping` |
| `$scope` | Model/ModelAndView |
| `ngRoute` module | Spring MVC DispatcherServlet routing |
| `$q` (promises) | `CompletableFuture` / reactive types |

---

## 26. Quick Reference — Interview Topics

### Common Fresher Questions:
- **What is AngularJS?** — Google's JS framework for SPAs using MVC, two-way binding, DI.
- **What is a directive?** — Marker on DOM that teaches HTML new tricks. Extends HTML vocabulary.
- **What is `$scope`?** — Object connecting controller and view. Holds model data.
- **What is `$rootScope`?** — Single root scope of the entire application, accessible everywhere.
- **`ng-if` vs `ng-show`?** — `ng-if` removes DOM, destroys scope. `ng-show` hides via CSS, keeps scope.
- **What is `$watch`?** — Registers a function to be called when an expression on scope changes.
- **What is scope hierarchy?** — Scopes form a tree mirroring the DOM. Child inherits from parent.
- **What is a module?** — Container for app components. Created with `angular.module('name', [deps])`.
- **What is two-way binding?** — Model and view stay in sync. Changes in either reflect in the other. Achieved via `ng-model`.

### Common Experienced Questions:
- **What is the digest cycle?** — AngularJS's change detection loop. Runs `$watch` listeners until stable.
- **`$http` vs `$resource`?** — `$http` is low-level AJAX. `$resource` is REST-oriented abstraction on top of `$http`.
- **How does DI work?** — `$injector` resolves dependencies by name. Use `$inject` for minification safety.
- **What is isolated scope?** — `scope: {}` in a directive — breaks prototypal inheritance. Used for reusable components.
- **Compile vs Link?** — Compile traverses DOM once per template (no scope). Link runs per instance (scope available).
- **What is `$apply`?** — Triggers digest cycle for code running outside AngularJS context.
- **How to improve performance?** — One-time binding, disable debug data, `track by`, fewer watchers, `useApplyAsync`.
- **`$` vs `$$`?** — `$` is public AngularJS API. `$$` is private internals — never use in app code.
- **Can parent access child controller methods?** — No. Child can access parent, not the other way around.
- **What are lifecycle hooks?** — `$onInit`, `$onChanges`, `$doCheck`, `$postLink`, `$onDestroy` in component controllers.

---

*Notes compiled from: [AngularJS Developer Guide](https://docs.angularjs.org/guide) | [AngularJS API Reference](https://docs.angularjs.org/api) | [AngularJS Tutorial](https://docs.angularjs.org/tutorial)*
