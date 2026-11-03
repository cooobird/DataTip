package com.cooobird.datatip.internal.util;

import com.cooobird.datatip.Datatip;
import com.mojang.logging.LogUtils;
import net.neoforged.fml.loading.FMLPaths;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

/**
 * 将编辑器 JSON Schema 导出到游戏目录，方便资源包作者直接引用。
 */
public final class SchemaExporter {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final String SCHEMA_RESOURCE = "/datatip.schema.json";
    private static final String SCHEMA_FILE_NAME = "datatip.schema.json";

    private SchemaExporter() {
    }

    @Nullable
    public static Path exportDefaultSchema() {
        Path output = FMLPaths.GAMEDIR.get().resolve(SCHEMA_FILE_NAME).toAbsolutePath();
        try (InputStream stream = Datatip.class.getResourceAsStream(SCHEMA_RESOURCE)) {
            if (stream == null) {
                LOGGER.warn("DataTip schema resource was not found in the mod jar");
                return null;
            }

            byte[] schemaBytes = stream.readAllBytes();
            if (Files.exists(output) && Arrays.equals(Files.readAllBytes(output), schemaBytes)) {
                return output;
            }

            Files.write(output, schemaBytes);
            LOGGER.info("Exported DataTip JSON schema to {}", output.toAbsolutePath());
            return output;
        } catch (IOException e) {
            LOGGER.warn("Failed to export DataTip JSON schema to {}", output.toAbsolutePath(), e);
            return null;
        }
    }
}
