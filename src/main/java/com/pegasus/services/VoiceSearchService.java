package com.pegasus.services;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.vosk.LibVosk;
import org.vosk.LogLevel;
import org.vosk.Model;
import org.vosk.Recognizer;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;
import java.io.InputStream;
import java.util.Properties;
import java.util.function.BooleanSupplier;

public class VoiceSearchService {
    private static final float SAMPLE_RATE = 16000.0f;
    private static final int CAPTURE_MILLIS = 5000;
    private static final String CONFIG_PATH = "/voice.properties";

    public String recognizeOnce() {
        return recognizeOnce(() -> false);
    }

    public String recognizeOnce(BooleanSupplier shouldStop) {
        String modelPath = resolveModelPath();
        if (modelPath == null) {
            throw new IllegalStateException("Set voice.voskModelPath in voice.properties or VOSK_MODEL_PATH env var.");
        }

        LibVosk.setLogLevel(LogLevel.WARNINGS);
        AudioFormat format = new AudioFormat(SAMPLE_RATE, 16, 1, true, false);
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

        try (Model model = new Model(modelPath);
             Recognizer recognizer = new Recognizer(model, SAMPLE_RATE)) {
            TargetDataLine line = (TargetDataLine) javax.sound.sampled.AudioSystem.getLine(info);
            line.open(format);
            line.start();
            byte[] buffer = new byte[4096];
            long end = System.currentTimeMillis() + CAPTURE_MILLIS;
            String latestResult = "";

            while (System.currentTimeMillis() < end && !shouldStop.getAsBoolean() && !Thread.currentThread().isInterrupted()) {
                int n = line.read(buffer, 0, buffer.length);
                if (n > 0) {
                    if (recognizer.acceptWaveForm(buffer, n)) {
                        latestResult = recognizer.getResult();
                    }
                }
            }
            line.stop();
            line.close();
            String finalText = extractText(recognizer.getFinalResult());
            if (!finalText.isBlank()) {
                return finalText;
            }
            String partialText = extractText(recognizer.getPartialResult());
            if (!partialText.isBlank()) {
                return partialText;
            }
            return extractText(latestResult);
        } catch (Exception e) {
            throw new IllegalStateException("Voice recognition failed: " + e.getMessage(), e);
        }
    }

    private String resolveModelPath() {
        String fromProps = readFromProperties();
        if (fromProps != null && !fromProps.startsWith("YOUR_")) {
            return fromProps;
        }
        String fromEnv = System.getenv("VOSK_MODEL_PATH");
        if (fromEnv == null || fromEnv.isBlank()) {
            return null;
        }
        return fromEnv.trim();
    }

    private String readFromProperties() {
        Properties properties = new Properties();
        try (InputStream is = VoiceSearchService.class.getResourceAsStream(CONFIG_PATH)) {
            if (is == null) {
                return null;
            }
            properties.load(is);
            String value = properties.getProperty("voice.voskModelPath");
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        } catch (Exception e) {
            return null;
        }
    }

    private String extractText(String json) {
        try {
            JsonObject object = JsonParser.parseString(json).getAsJsonObject();
            if (object.has("text") && !object.get("text").isJsonNull()) {
                return object.get("text").getAsString().trim();
            }
            if (object.has("partial") && !object.get("partial").isJsonNull()) {
                return object.get("partial").getAsString().trim();
            }
        } catch (Exception ignored) {
        }
        return "";
    }
}
