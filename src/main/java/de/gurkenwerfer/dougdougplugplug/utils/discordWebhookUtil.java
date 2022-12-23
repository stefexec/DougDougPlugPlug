package de.gurkenwerfer.dougdougplugplug.utils;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static de.gurkenwerfer.dougdougplugplug.filemanager.buildModePermConfig.getConfigFile;

public class discordWebhookUtil {
    static String webhookURL = getConfigFile().getString("webhook-url");

    public static void sendCommand(final String command) {

        try {
            final HttpsURLConnection connection = (HttpsURLConnection) new URL(webhookURL).openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; U; Linux i686) Gecko/20071127 Firefox/2.0.0.11");
            connection.setDoOutput(true);

            try (final OutputStream outputStream = connection.getOutputStream()) {

                String preparedCommand = command.replaceAll("\\\\", "");
                if (preparedCommand.endsWith(" *")) preparedCommand = preparedCommand.substring(0, preparedCommand.length() - 2) + "*";
                outputStream.write(("{\"content\":\"" + "`" + preparedCommand + "`" + "\"}").getBytes(StandardCharsets.UTF_8));
            }
            connection.getInputStream();
        } catch (final IOException ignored) {}
    }

    public static void sendEmbed(final String title, final String description, final Integer color, final String name1, final String value1, final String name2, final String value2, final String name3, final String value3, final String name4, final String value4, final UUID uuid) {

        try {
            final HttpsURLConnection connection = (HttpsURLConnection) new URL(webhookURL).openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; U; Linux i686) Gecko/20071127 Firefox/2.0.0.11");
            connection.setDoOutput(true);

            try (final OutputStream outputStream = connection.getOutputStream()) {

                outputStream.write(("{\"embeds\":[{\"title\":\""
                        + title + "\",\"description\":\""
                        + description + "\",\"color\":"
                        + color + ",\"fields\":[{\"name\":\""
                        + name1 + "\",\"value\":\"" + "`"
                        + value1 + "`" + "\",\"inline\":true},{\"name\":\""
                        + name2 + "\",\"value\":\"" + "`"
                        + value2 + "`" + "\",\"inline\":true},{\"name\":\""
                        + name3 + "\",\"value\":\""
                        + value3 + "\"},{\"name\":\""
                        + name4 + "\",\"value\":\""
                        + value4 + "\"}],\"thumbnail\":{\"url\":\"https://mc-heads.net/avatar/"
                        + uuid + ".png\"}}]}"
                ).getBytes(StandardCharsets.UTF_8));
            }
            connection.getInputStream();
        } catch (final IOException ignored) {}
    }

    public static void sendUserWarning(final String title, final String description, final Integer color, final UUID uuid) {

        try {
            final HttpsURLConnection connection = (HttpsURLConnection) new URL(webhookURL).openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; U; Linux i686) Gecko/20071127 Firefox/2.0.0.11");
            connection.setDoOutput(true);

            try (final OutputStream outputStream = connection.getOutputStream()) {

                outputStream.write(("{\"embeds\":[{\"title\":\""
                        + title + "\",\"description\":\""
                        + description + "\",\"color\":"
                        + color + ",\"thumbnail\":{\"url\":\"https://mc-heads.net/avatar/"
                        + uuid + ".png\"}}]}"
                ).getBytes(StandardCharsets.UTF_8));
            }

            connection.getInputStream();

        } catch (final IOException ignored) {}
    }

    public static void sendWarning(final String title, final String description, final Integer color, final String iconUrl) {

        try {
            final HttpsURLConnection connection = (HttpsURLConnection) new URL(webhookURL).openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; U; Linux i686) Gecko/20071127 Firefox/2.0.0.11");
            connection.setDoOutput(true);

            try (final OutputStream outputStream = connection.getOutputStream()) {

                outputStream.write(("{\"embeds\":[{\"title\":\""
                        + title + "\",\"description\":\""
                        + description + "\",\"color\":"
                        + color + ",\"thumbnail\":{\"url\":\""
                        + iconUrl + "\"}}]}"
                ).getBytes(StandardCharsets.UTF_8));
            }
            connection.getInputStream();
        } catch (final IOException ignored) {}
    }
}