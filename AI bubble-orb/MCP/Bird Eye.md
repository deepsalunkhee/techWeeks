# Model Context Protocol (MCP) 

---

## 1. What is MCP?

**MCP (Model Context Protocol)** is an open protocol that standardizes how Large Language Models (LLMs) securely discover, invoke, and interact with external tools, services, and data sources.

It defines:

- How tools are **described**
    
- How they are **called**
    
- How results are **returned**
    

MCP is a **protocol**, not a model, framework, or agent.

---

## 2. Why MCP Exists

Before MCP:

- Tool integrations were ad-hoc and tightly coupled
    
- Each AI app implemented custom glue code
    
- Switching models required rewriting integrations
    

MCP solves this by:

- Decoupling **reasoning (LLM)** from **capabilities (tools)**
    
- Making tool access reusable and model-agnostic
    
- Enforcing structured, secure interaction
    

Analogy:

- JDBC for databases
    
- HTTP for services
    
- MCP for AI ↔ tools
    

---

## 3. Core Components of MCP

### 3.1 MCP Server

- A normal backend process (Node, Python, Java, etc.)
    
- Exposes tools with:
    
    - Name
        
    - Description
        
    - Input schema
        
    - Output schema
        
- Internally calls real APIs, databases, or services
    
- Has **no AI logic**
    

Example tools:

- `get_captions(video_id)`
    
- `read_file(path)`
    
- `query_database(sql)`
    

---

### 3.2 MCP Client

- Lives inside the AI application or agent
    
- Responsibilities:
    
    - Connect to MCP servers
        
    - Discover tool metadata
        
    - Validate tool inputs
        
    - Execute tool calls
        
    - Return results to the LLM
        

---

### 3.3 MCP Protocol

- Communication standard (JSON-RPC)
    
- Transport:
    
    - stdio (local)
        
    - HTTP / WebSocket (remote)
        
- Fully structured and machine-readable
    

---

## 4. Where AI Agents Fit

An **AI Agent** is a control loop around an LLM.

Typical agent components:

- LLM (reasoning)
    
- Memory / state
    
- Planner / loop
    
- MCP Client (tool execution layer)
    

MCP fits **inside the agent**, not outside it.

Agent responsibilities:

- Decide _what_ to do
    
- Decide _which_ tool to use
    
- Decide _when_ to stop
    

MCP responsibilities:

- Execute tools safely and consistently
    

---

## 5. How an LLM Learns About Tools

1. MCP Client connects to MCP Server
    
2. MCP Server exposes tool metadata
    
3. MCP Client injects tool descriptions into LLM system context
    

The LLM does **not** discover tools by itself.  
It only sees what the MCP Client provides.

---

## 6. How Tool Invocation Works (Exact Flow)

### Step 1: User Prompt

Example:

> “Summarize this YouTube video”

---

### Step 2: LLM Reasons

- Needs video content
    
- Sees available tool: `get_captions`
    
- Decides to call it
    

---

### Step 3: LLM Outputs Structured Tool Call

```json
{
  "tool_call": {
    "name": "get_captions",
    "arguments": {
      "video_id": "abc123"
    }
  }
}
```

The LLM does **not** call the server directly.

---

### Step 4: MCP Client Executes the Call

- Validates arguments against schema
    
- Sends request to MCP Server via protocol
    

---

### Step 5: MCP Server Runs Real Code

Example (inside server):

```python
youtube_api.fetch_captions(video_id)
```

This is normal backend logic.

---

### Step 6: MCP Server Returns Result

```json
{
  "captions": "Welcome to this video..."
}
```

---

### Step 7: Result Is Sent Back to LLM

- MCP Client adds result to LLM context
    
- LLM continues reasoning
    

---

### Step 8: LLM Produces Final Answer

Example:

> “This video explains three main ideas…”

---

## 7. How the LLM Knows What to Do With Tool Data

- Tool results are added to the conversation context
    
- LLM is trained to:
    
    - Observe results
        
    - Reason further
        
    - Decide next action or final output
        

No special logic is required in the tool or server.

---

## 8. Responsibility Separation (Critical)

| Responsibility         | Component  |
| ---------------------- | ---------- |
| Tool discovery         | MCP Client |
| Tool description       | MCP Server |
| Tool choice            | LLM        |
| Input validation       | MCP Client |
| API execution          | MCP Server |
| Security & permissions | MCP Server |
| Understanding results  | LLM        |
| Final response         | LLM        |

---

## 9. MCP with Existing Applications (YouTube Example)

You do NOT rewrite existing logic.

You:

1. Take existing YouTube service code
    
2. Wrap it inside an MCP Server
    
3. Expose clean tool contracts
    
4. Let AI agents use those tools via MCP
    

This allows:

- Reuse across multiple AI apps
    
- Safe API access
    
- Easy model replacement
    

---

## 10. Key Takeaways (Note-Ready)

- MCP standardizes how AI models interact with tools
    
- LLMs decide **when and what** to call
    
- MCP Servers execute **how**
    
- MCP Clients handle communication and validation
    
- AI Agents use MCP as their execution layer
    
- Reasoning stays inside the model; capabilities live outside
    

---

## 11. One-Line Mental Model

**Agent decides.  
MCP executes.  
Server enforces.  
Model reasons.**

---
