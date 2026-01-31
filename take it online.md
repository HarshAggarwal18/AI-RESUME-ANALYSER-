# 🚀 Take It Online: Migrating Spring AI to Groq Cloud

This guide will help you migrate your Spring Boot application from using a local Ollama instance to the cloud-based Groq API. This is perfect for deploying your app to Render, as it removes the need for a heavy local AI server.

## 📋 Prerequisites

1.  **Groq Account**: Sign up at [console.groq.com](https://console.groq.com/).
2.  **API Key**: Create a new API Key in the Groq console. Copy it immediately (you won't see it again).

---

## 🛠️ Step 1: Update Dependencies (`pom.xml`)

We need to switch from the **Ollama** starter to the **OpenAI** starter. Spring AI uses the `openai` client to talk to Groq because Groq is API-compatible.

**Remove or Comment out:**

```xml
<!-- ❌ Remove Ollama -->
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-model-ollama</artifactId>
</dependency>
```

**Add:**

```xml
<!-- ✅ Add OpenAI (Compatible with Groq) -->
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-openai-spring-boot-starter</artifactId>
</dependency>
```

---

## ⚙️ Step 2: Configure `application.properties`

Update your configuration to point to Groq's servers instead of localhost.

**Comment out Ollama settings:**

```properties
#ollama
#spring.ai.ollama.base-url=http://localhost:11434
#spring.ai.ollama.chat.options.model=qwen2.5:0.5b-instruct
```

**Add Groq (OpenAI) settings:**

```properties
# 🚀 Groq Configuration
spring.ai.openai.base-url=https://api.groq.com/openai
spring.ai.openai.api-key=${GROQ_API_KEY}
spring.ai.openai.chat.options.model=llama3-70b-8192
spring.ai.openai.chat.options.temperature=0.7
```

> **Note:**
> *   `llama3-70b-8192` is a powerful, fast model on Groq. You can also use `llama3-8b-8192` or `mixtral-8x7b-32768`.
> *   `${GROQ_API_KEY}` tells Spring Boot to look for an environment variable. **DO NOT** hardcode your key in the file if you are pushing to valid git repo.

---

## ☁️ Step 3: Deployment Configure (Render)

When you deploy to Render (or run locally with the new config), you need to provide the authentication key.

### **For Local Testing (IntelliJ/Terminal):**
You need to set the environment variable.
*   **IntelliJ**: Run Configuration -> Environment Variables -> Add `GROQ_API_KEY=gsk_...`
*   **Terminal (PowerShell)**: `$env:GROQ_API_KEY="gsk_..."` then run `mvn spring-boot:run`

### **For Render Deployment:**

1.  Go to your **Service Dashboard** on Render.
2.  Click **Environment** tab.
3.  Click **Add Environment Variable**.
4.  **Key**: `GROQ_API_KEY`
5.  **Value**: `gsk_your_actual_key_here`
6.  Save changes. Render will likely redeploy.

---

## 🧪 Verification

Once deployed, your `ResumeParserService` and `AIAnalyzerService` will automatically send requests to Groq instead of your local machine.

*   **API Latency**: Groq is extremely fast, so you should see much faster responses than local Ollama!
*   **Model**: Ensure the model name in `application.properties` exists on Groq (check [Groq Models](https://console.groq.com/docs/models)).

### Troubleshooting
*   **401 Unauthorized**: Check your API Key.
*   **404 Not Found**: Check the `spring.ai.openai.base-url` is exactly `https://api.groq.com/openai`.
*   **Model not found**: Check that `spring.ai.openai.chat.options.model` matches a valid Groq model name exactly.
