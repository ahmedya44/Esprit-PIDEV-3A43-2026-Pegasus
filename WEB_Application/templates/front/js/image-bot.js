(function () {
  const fab = document.getElementById("imgbotFab");
  const panel = document.getElementById("imgbotPanel");
  const closeBtn = document.getElementById("imgbotClose");
  const backdrop = document.getElementById("imgbotBackdrop");

  const input = document.getElementById("imgbotInput");
  const send = document.getElementById("imgbotSend");
  const msgs = document.getElementById("imgbotMsgs");
  const images = document.getElementById("imgbotImages");
  const loading = document.getElementById("imgbotLoading");

  function openPanel() {
    panel.classList.add("open");
    panel.setAttribute("aria-hidden", "false");
    backdrop.style.display = "block";
    setTimeout(() => input?.focus(), 50);
  }

  function closePanel() {
    panel.classList.remove("open");
    panel.setAttribute("aria-hidden", "true");
    backdrop.style.display = "none";
  }

  function addMsg(text, who) {
    const div = document.createElement("div");
    div.className = "imgbot-msg " + who;
    div.innerHTML = text;
    msgs.appendChild(div);
    msgs.scrollTop = msgs.scrollHeight;
  }

  async function generateImage(prompt) {
    loading.style.display = "block";
    try {
      const res = await fetch("/api/image/generate", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ prompt })
      });

      const data = await res.json();
      if (!res.ok) {
        throw new Error(data?.error || "Failed");
      }

      // support: {url: "..."} OR {urls: ["...","..."]}
      const urls = data.urls || (data.url ? [data.url] : []);
      if (!urls.length) {
        addMsg("No image returned. Try another prompt.", "bot");
        return;
      }

      urls.forEach(u => {
        const img = document.createElement("img");
        img.src = u;
        img.alt = "Generated image";
        images.prepend(img);
      });

      addMsg("✅ Done! Want another one?", "bot");
    } catch (e) {
      addMsg("❌ " + (e.message || "Error generating image"), "bot");
    } finally {
      loading.style.display = "none";
    }
  }

  function onSend() {
    const prompt = (input.value || "").trim();
    if (!prompt) return;

    addMsg(prompt, "user");
    input.value = "";
    generateImage(prompt);
  }

  fab?.addEventListener("click", openPanel);
  closeBtn?.addEventListener("click", closePanel);
  backdrop?.addEventListener("click", closePanel);

  send?.addEventListener("click", onSend);
  input?.addEventListener("keydown", (e) => {
    if (e.key === "Enter") onSend();
    if (e.key === "Escape") closePanel();
  });

  document.addEventListener("keydown", (e) => {
    if (e.key === "Escape") closePanel();
  });
})();