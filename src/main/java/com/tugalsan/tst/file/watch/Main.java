package com.tugalsan.tst.file.watch;

import com.tugalsan.api.file.server.*;
import com.tugalsan.api.log.server.*;
import com.tugalsan.api.thread.server.async.await.TS_ThreadAsyncAwait;
import com.tugalsan.api.thread.server.sync.TS_ThreadSyncTrigger;
import com.tugalsan.api.thread.server.sync.TS_ThreadSyncWait;
import java.io.IOException;
import static java.lang.System.out;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;

public class Main {

    private static final TS_Log d = TS_Log.of(Main.class);

    //cd  C:\me\codes\com.tugalsan\tst\com.tugalsan.tst.file.watch
    //java --enable-preview --add-modules jdk.incubator.vector -jar target/com.tugalsan.tst.file.watch-1.0-SNAPSHOT-jar-with-dependencies.jar
    public static void main(String... s) {
        TS_ThreadSyncTrigger killTrigger = TS_ThreadSyncTrigger.of("main");
        if (true) {
            return;
        }
        var file = Path.of("C:\\dat\\sql\\rql\\TS_LibRqlBufferUtils_autosqlweb2.json");

        d.cr("main", "isLocked", TS_FileUtils.isFileLocked(file));
        if (true) {
            return;
        }

        TS_FileWatchUtils.file(killTrigger, file, () -> {
            d.cr("main", "create detected", file);
        }, 60, TS_FileWatchUtils.Triggers.CREATE, TS_FileWatchUtils.Triggers.MODIFY);
        TS_ThreadAsyncAwait.runUntil(killTrigger.newChild("test"), Duration.ofMinutes(1), kt -> {
            TS_ThreadSyncWait.seconds(d.className, killTrigger, 10);
            d.cr("main", "alive");
        });
    }
}
