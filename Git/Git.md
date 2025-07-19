
---

# 📚 **Complete Git Notes(I Wish)**

---

## 🚀 **1. What is Git?**

- **Git** is a **distributed version control system (DVCS)**.
    
- It lets multiple developers:
    
    - Track changes to code
        
    - Work in parallel using **branches**
        
    - Merge code safely
        
    - Roll back mistakes easily
        

---

## 🗂️ **2. Core Concepts**

|Concept|Meaning|
|---|---|
|**Repository (Repo)**|The whole project tracked by Git.|
|**Working Directory**|The files you’re editing locally.|
|**Staging Area (Index)**|The snapshot of files ready to commit.|
|**Commit**|A snapshot of staged files.|
|**Branch**|A pointer to a line of commits.|
|**HEAD**|The current branch/commit you’re working on.|
|**Remote**|A copy of the repo on a server (e.g., GitHub).|

---

## ⚡ **3. Typical Git Workflow**

1. Edit files (working directory)
    
2. `git add` to stage them
    
3. `git commit` to save snapshot
    
4. `git push` to update remote repo
    
5. `git pull` to get latest from remote
    

---

## 🧑‍💻 **4. Basic Git Commands**

|Command|What it does|
|---|---|
|`git init`|Create a new Git repo|
|`git clone <url>`|Copy a remote repo locally|
|`git status`|Show changes & staging status|
|`git add <file>`|Stage a file|
|`git add .`|Stage all changes|
|`git commit -m "msg"`|Commit staged changes|
|`git log`|View commit history|
|`git push origin main`|Push commits to remote branch|
|`git pull origin main`|Fetch & merge changes from remote|

---

## 🌿 **5. Branching**

### ✅ **Create a branch**

```bash
git branch new-feature
```

### ✅ **Switch branch**

```bash
git checkout new-feature
```

or

```bash
git switch new-feature
```

### ✅ **Create & switch**

```bash
git checkout -b new-feature
```

### ✅ **Example**

```bash
git checkout main
git checkout -b feature/login
```

This means:

- `feature/login` starts at the same commit as `main`.
    
- The **new branch** has all code/history up to that point.
    

---

## 🔄 **6. What happens when you `checkout`**

|🔑|You’re just **switching HEAD** to point to another branch.|
|---|---|
|📂|Your working directory updates to match that branch’s files.|
|✅|No merging or updating other branches automatically.|

---

### 📌 **Example**

1. On `main`
    
2. `git checkout -b feature/new`
    
    - `feature/new` = copy of `main`’s snapshot.
        
3. Make changes → `git commit` on `feature/new`
    
4. `git checkout main`
    
    - Now your working directory rolls back to match `main` (which does NOT have the new commits yet).
        

---

## 🔗 **7. How commits stay on branches**

- Commits are attached to the branch you commit them on.
    
- Switching branches with `git checkout` **does not move commits**.
    
- To transfer commits → use `git merge` or `git cherry-pick`.
    

✅ **Example**

```
A -- B -- C   (main)
          \
           D  (feature/new)
```

- `main` sees up to `C`.
    
- `feature/new` sees `C` + `D`.
    

Switching between them toggles which snapshot you see.

---

## ✏️ **8. `git commit --amend`**

|✔️|Modify your last commit|
|---|---|
|🗂️|Add missed files|
|📝|Edit the commit message|
|🔄|Replaces last commit with a new one (new SHA)|

---

### ✅ **Basic usage**

```bash
# Add forgotten file
git add forgot.js

# Amend commit with new changes
git commit --amend
```

Or update just the message:

```bash
git commit --amend -m "Better message"
```

---

### ⚡ **Amend rules**

|✅ Safe|❌ Dangerous|
|---|---|
|Before push|After push (needs force push!)|

If you amend after pushing:

```bash
git push --force
```

⚠️ Be careful! Force pushing can overwrite others’ work.

---

## 🧹 **9. `gitk`: Git’s Built-in Visual Tool**

|🗂️|**`gitk`** is a GUI history viewer.|
|---|---|
|✔️|Shows commit tree, branches, merges visually|
|🔍|Inspect diffs for each commit|
|🕵️|Good for understanding complicated histories|

### ✅ How to use

```bash
gitk            # Show history of current branch
gitk --all      # Show all branches
gitk <file>     # Show changes for a file
```

Opens a separate window with an interactive commit graph.

---

## 📝 **10. Important Tips**

✅ `git checkout`

- Just moves your HEAD pointer — no automatic merges!
    

✅ `git commit --amend`

- Changes your last commit.
    
- Great for fixing small mistakes.
    
- Safe only before pushing.
    

✅ `gitk`

- A simple way to visualize commits and branches.
    

✅ **Best Practice**

- Always check `git status` and `git log` before committing or switching branches.
    
- Use `git stash` if you have local changes you don’t want to commit yet but need to switch branches:
    
    ```bash
    git stash
    git checkout otherbranch
    git stash pop
    ```
    

---

## 📌 **Handy Commands Recap**

|Task|Command|
|---|---|
|Init repo|`git init`|
|Clone repo|`git clone <url>`|
|Stage file|`git add <file>`|
|Commit|`git commit -m "msg"`|
|Amend commit|`git commit --amend`|
|Create branch|`git branch <name>`|
|Create & switch|`git checkout -b <name>`|
|Switch|`git checkout <name>`|
|Merge|`git merge <name>`|
|Visualize|`git log --oneline --graph --all` or `gitk`|

---

## 🎯 **Example Workflow**

```bash
# Create new branch
git checkout -b feature/login

# Make changes & commit
git add .
git commit -m "Add login page"

# Oops! Forgot a file
git add forgot.js
git commit --amend -m "Add login page with JS validation"

# Switch back
git checkout main

# Merge changes
git merge feature/login

# View with gitk
gitk --all
```

---

## ✅ **That’s it!**

You now have a **clear understanding** of:

- Core Git flow
    
- Branching & switching
    
- How commits move (or don’t!)
    
- How `amend` works
    
- How to visualize your history with `gitk`
    

---
