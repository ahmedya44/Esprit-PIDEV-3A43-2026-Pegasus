document.addEventListener("DOMContentLoaded", () => {
  const fab = document.getElementById("imgbotFab");
  const panel = document.getElementById("imgbotPanel");
  const closeBtn = document.getElementById("imgbotClose");
  const input = document.getElementById("imgbotInput");
  const sendBtn = document.getElementById("imgbotSend");
  const msgs = document.getElementById("imgbotMsgs");

  if (!fab || !panel || !closeBtn || !input || !sendBtn || !msgs) return;

  const scrollToBottom = () => {
    msgs.scrollTop = msgs.scrollHeight;
  };

  const open = () => {
    panel.classList.add("open");
    panel.setAttribute("aria-hidden", "false");
    window.setTimeout(() => input.focus(), 120);
  };

  const close = () => {
    panel.classList.remove("open");
    panel.setAttribute("aria-hidden", "true");
  };

  const addMsg = (text, who = "bot", extraClass = "") => {
    const div = document.createElement("div");
    div.className = `imgbot-msg ${who}${extraClass ? ` ${extraClass}` : ""}`;
    div.innerHTML = text;
    msgs.appendChild(div);
    scrollToBottom();
    return div;
  };

  const addImage = (src) => {
    const div = document.createElement("div");
    div.className = "imgbot-msg bot";
    div.innerHTML = `<img src="${src}" alt="Generated image" class="imgbot-image">`;
    msgs.appendChild(div);
    scrollToBottom();
  };

  const setSending = (isSending) => {
    sendBtn.disabled = isSending;
    input.disabled = isSending;
  };

  const send = async () => {
    const prompt = input.value.trim();
    if (!prompt) return;

    addMsg(prompt, "user");
    input.value = "";
    setSending(true);
    const loadingEl = addMsg("Generating image...", "bot", "loading");

    try {
      const res = await fetch("/api/image-bot/generate", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ prompt }),
      });

      const data = await res.json();
      loadingEl.remove();

      if (!res.ok) {
        addMsg(`Error: ${data.error || "Request failed"}`, "bot");
        return;
      }

      if (data.url) {
        addImage(data.url);
      } else if (data.b64) {
        addImage(`data:image/png;base64,${data.b64}`);
      } else {
        addMsg("Error: no image was returned.", "bot");
      }
    } catch (_error) {
      loadingEl.remove();
      addMsg("Network error. Please try again.", "bot");
    } finally {
      setSending(false);
      input.focus();
    }
  };

  fab.addEventListener("click", open);
  closeBtn.addEventListener("click", close);
  sendBtn.addEventListener("click", send);

  input.addEventListener("keydown", (event) => {
    if (event.key === "Enter") send();
  });

  document.addEventListener("keydown", (event) => {
    if (event.key === "Escape") close();
  });
});
