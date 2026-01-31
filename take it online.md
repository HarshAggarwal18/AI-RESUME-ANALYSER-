# 🚀 Take It Online: Migrating Spring AI to Groq Cloud

This guide explains how to migrate your Spring Boot application from a local Ollama instance to the cloud-based **Groq API**. It also covers how to properly handle your API keys locally (using `.env`) and in production (Render).

---

## 📋 Prerequisites

1.  **Groq Account**: Sign up at [console.groq.com](https://console.groq.com/).
2.  **API Key**: Create a new API Key in the Groq console. Copy it immediately.

---

## 🛠️ Step 1: Update Dependencies (`pom.xml`)

We need two things:
1.  **OpenAI Starter**: Spring AI uses the OpenAI client to talk to Groq.
2.  **Spring Dotenv**: To automatically load your `.env` file locally.

**Modify your `pom.xml` dependencies:**

```xml
<!-- ❌ Remove or Comment out Ollama -->
<!--
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-model-ollama</artifactId>
</dependency>
-->

<!-- ✅ Add OpenAI (Compatible with Groq) -->
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-model-openai</artifactId>
</dependency>

<!-- ✅ Add Spring Dotenv (Loads .env properties) -->
<dependency>
    <groupId>me.paulschwarz</groupId>
    <artifactId>spring-dotenv</artifactId>
    <version>4.0.0</version>
</dependency>
```

---

## ⚙️ Step 2: Configure Environment Variables

### 🏡 Local Development (The `.env` file)

1.  **Create a file named `.env`** in the root of your project (same level as `pom.xml`).
2.  **Add your API key** to it:

    ```properties
    GROQ_API_KEY=gsk_your_actual_key_here
    ```

3.  **Ensure `.env` is ignored by Git**:
    Check your `.gitignore` file and ensure it contains `.env`. This prevents your secret key from being leaked.

    ```gitignore
    .env
    ```

**How it works:** thanks to the `spring-dotenv` dependency, when you run the app locally, it will automatically read this file and make `GROQ_API_KEY` available to Spring Boot.

### ☁️ Production / Render Deployment

**IMPORTANT:** The `.env` file is **NOT** uploaded to Render (because it's git-ignored). You must manually configure the environment variable on the server.

1.  Go to your **Service Dashboard** on Render.
2.  Click the **Environment** tab.
3.  Click **Add Environment Variable**.
4.  **Key**: `GROQ_API_KEY`
5.  **Value**: `gsk_your_actual_key_here` (Copy from Groq console)
6.  Save changes. Render will redeploy your service.

---

## ⚙️ Step 3: Configure `application.properties`

Update your configuration to use the variable we just set up.

```properties
# 🚀 Groq Configuration
spring.ai.openai.base-url=https://api.groq.com/openai
spring.ai.openai.api-key=${GROQ_API_KEY}
spring.ai.openai.chat.options.model=llama3-70b-8192
spring.ai.openai.chat.options.temperature=0.7

# Disable Ollama (if present)
# spring.ai.ollama.base-url=http://localhost:11434
```

---

## 🧪 Verification & Troubleshooting

### Local Run
Run `mvn spring-boot:run`.
- **Success**: App starts, and logs show no errors regarding missing API keys.
- **Micro-test**: If you have a test controller, hit the endpoint. It should respond using Groq.
- **Failure**: If you see `${GROQ_API_KEY}` in the error, it means the variable wasn't resolved. Check if `spring-dotenv` is in `pom.xml` and `.env` exists in the project root.

### Render Deployment
- **401 Unauthorized**: You forgot to set the Environment Variable in the Render Dashboard, or the key is invalid.
- **404 Not Found**: Ensure `spring.ai.openai.base-url` is exactly `https://api.groq.com/openai`.

---
**Summary**:
- **Local**: Helper library reads `.env` -> Spring Boot sees `GROQ_API_KEY`.
- **Render**: Dashboard injection -> Spring Boot sees `GROQ_API_KEY`.
- **Code**: `application.properties` just uses `${GROQ_API_KEY}` and doesn't care where it came from.
