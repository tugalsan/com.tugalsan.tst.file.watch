package com.tugalsan.tst.file.watch;

import com.tugalsan.api.file.server.*;
import com.tugalsan.api.log.server.*;
import com.tugalsan.api.thread.server.async.TS_ThreadAsyncAwait;
import com.tugalsan.api.thread.server.TS_ThreadWait;
import com.tugalsan.api.thread.server.sync.TS_ThreadSyncTrigger;
import java.nio.file.Path;
import java.time.Duration;

public class Main {

    private static final TS_Log d = TS_Log.of(Main.class);

    //cd  C:\me\codes\com.tugalsan\tst\com.tugalsan.tst.file.watch
    //java --enable-preview --add-modules jdk.incubator.vector -jar target/com.tugalsan.tst.file.watch-1.0-SNAPSHOT-jar-with-dependencies.jar
    public static void main(String... s) {
        TS_ThreadSyncTrigger killTrigger = TS_ThreadSyncTrigger.of();
        var file = Path.of("C:\\dat\\sql\\rql\\TS_LibRqlBufferUtils_autosqlweb2.json");

        d.cr("main", "isLocked", TS_FileUtils.isFileLocked(file));

        if (true) {
            return;
        }

        TS_FileWatchUtils.file(killTrigger, file, () -> {
            d.cr("main", "create detected", file);
        }, 60, TS_FileWatchUtils.Triggers.CREATE, TS_FileWatchUtils.Triggers.MODIFY);
        TS_ThreadAsyncAwait.runUntil(killTrigger, Duration.ofMinutes(1), kt -> {
            TS_ThreadWait.seconds(d.className, killTrigger, 10);
            d.cr("main", "alive");
        });
    }
}
