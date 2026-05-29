package org.magicteam.datatip.watcher;

import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.*;
import java.util.function.Consumer;

/**
 * 监听 config/datatip.json 的改动，文件变了就回调。
 */
public class FileWatcher {
    private static final Logger LOGGER = LogUtils.getLogger();

    /**
     * 启动一个 daemon 线程监听文件变化。
     * @param filePath  要监听的文件
     * @param onChange  文件变化时干啥
     */
    public static void watch(Path filePath, Runnable onChange) throws IOException {
        if (!Files.exists(filePath)) return;

        WatchService ws = FileSystems.getDefault().newWatchService();
        // 监听文件所在的目录
        filePath.getParent().register(ws, StandardWatchEventKinds.ENTRY_MODIFY);

        Thread t = new Thread(() -> {
            try {
                while (true) {
                    var key = ws.take();
                    for (var ev : key.pollEvents()) {
                        if (ev.kind() == StandardWatchEventKinds.ENTRY_MODIFY
                            && filePath.getFileName().toString()
                            .equals(((Path) ev.context()).getFileName().toString())) {
                            onChange.run();
                            LOGGER.info("datatip.json 变了，已重载");
                        }
                    }
                    key.reset();
                }
            } catch (InterruptedException | ClosedWatchServiceException ignored) {}
        }, "datatip-watcher");
        t.setDaemon(true);
        t.start();
    }
}
