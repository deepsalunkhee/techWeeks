

## 🧩 **1. What is a Merge Conflict?**

A merge conflict happens when Git can’t automatically combine changes from two branches because both branches modified the **same lines** of a file. Git then asks _you_ to resolve it manually

---

## 🛠️ **2. When Conflicts Occur**

Merge conflicts can appear during:

- `git merge`
    
- `git pull`
    
- `git rebase`
    
- `git cherry-pick`  
    If the changes overlap on the **same lines**, Git informs you that there are conflicts. ([JetBrains](https://www.jetbrains.com/help/idea/resolve-conflicts.html?utm_source=chatgpt.com "Resolve Git conflicts | IntelliJ IDEA Documentation"))
    

---

## 📦 **3. Starting the Merge in IntelliJ IDEA**

1. Open the **Git Tool Window** (`Alt-9` / `⌘9`).
    
2. Select your **target branch** (e.g., `main`).
    
3. Merge the **feature branch** into it.
    
4. If conflicts exist, IntelliJ shows a **Conflicts dialog** telling you there are conflicts. ([JetBrains](https://www.jetbrains.com/help/idea/resolve-conflicts.html?utm_source=chatgpt.com "Resolve Git conflicts | IntelliJ IDEA Documentation"))
    

---

## 🧠 **4. Conflicts Dialog — Your First Options**

In the Conflict dialog, you can choose:

- **Accept Yours** — Keep your branch’s version and discard the incoming changes.
    
- **Accept Theirs** — Keep the other branch’s version and discard your changes.
    
- **Merge** — Open the merge editor to manually resolve conflicts. ([JetBrains](https://www.jetbrains.com/help/idea/resolve-conflicts.html?utm_source=chatgpt.com "Resolve Git conflicts | IntelliJ IDEA Documentation"))
    

⚠️ _Only use “Accept Yours/Theirs” if you’re certain the other side’s changes aren’t needed._ ([JetBrains](https://www.jetbrains.com/help/idea/resolve-conflicts.html?utm_source=chatgpt.com "Resolve Git conflicts | IntelliJ IDEA Documentation"))

---

## 🔍 **5. The Merge Editor**

When you click **Merge**, IntelliJ opens a visual three-pane editor: ([JetBrains](https://www.jetbrains.com/help/idea/resolve-conflicts.html?utm_source=chatgpt.com "Resolve Git conflicts | IntelliJ IDEA Documentation"))

### 👈 Left Pane

Represents the **current branch’s version**.

### 👉 Right Pane

Shows the **incoming branch’s changes**.

### 🧠 Middle Pane

Shows the **result** being constructed.

---

## 🔄 **6. How to Resolve Changes**

Inside the merge editor:

- **Green/Blue/Gray highlights**: indicate additions, changes, and deletions.
    
- **Red highlights**: indicate conflicts that must be resolved manually.
    
- Use the **accept arrows** to bring in a change from left or right side into the result.
    
- You can also **ignore** a change or manually edit within the central pane.
    
- Non-conflicting changes can be applied automatically with **Apply All Non-Conflicting Changes**. ([JetBrains](https://www.jetbrains.com/help/idea/resolve-conflicts.html?utm_source=chatgpt.com "Resolve Git conflicts | IntelliJ IDEA Documentation"))
    

---

## ✔️ **7. Finishing the Merge**

Once all conflicts are processed:

1. Click **Apply** to save the merged result.
    
2. IntelliJ will mark the conflicts as resolved.
    
3. Now you can **commit** the merge normally. ([JetBrains](https://www.jetbrains.com/help/idea/resolve-conflicts.html?utm_source=chatgpt.com "Resolve Git conflicts | IntelliJ IDEA Documentation"))
    

---

## 💡 **8. Useful Tips**

🧩 **Undo**: You can use `Ctrl+Z` (Windows/Linux) or `⌘Z` (macOS) in the merge editor if you make a mistake. ([JetBrains](https://www.jetbrains.com/help/idea/resolve-conflicts.html?utm_source=chatgpt.com "Resolve Git conflicts | IntelliJ IDEA Documentation"))  
📄 **Edit Directly**: You may directly edit the result in the central pane if the automated options don’t give the correct code. ([JetBrains](https://www.jetbrains.com/help/idea/resolve-conflicts.html?utm_source=chatgpt.com "Resolve Git conflicts | IntelliJ IDEA Documentation"))

---

## 🧾 **9. Behind the Scenes**

Even if the merge dialog is closed, IntelliJ keeps conflicted files listed under **Local Changes → Merge Conflicts** so you can resolve them later. ([JetBrains](https://www.jetbrains.com/help/idea/resolve-conflicts.html?utm_source=chatgpt.com "Resolve Git conflicts | IntelliJ IDEA Documentation"))

---

## ✅ Summary of the Workflow

1. Trigger a merge (e.g., feature → main).
    
2. Git detects conflicts.
    
3. IntelliJ shows a **Conflicts dialog**.
    
4. Choose to manually **Merge** in a visual editor.
    
5. Use left/right/editor panes to pick or combine changes.
    
6. Apply and commit once resolved. ([JetBrains](https://www.jetbrains.com/help/idea/resolve-conflicts.html?utm_source=chatgpt.com "Resolve Git conflicts | IntelliJ IDEA Documentation"))
    

---
