package other.test;

import java.io.IOException;
import java.nio.file.*;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class CopyMp3 {
    public static void main(String[] args) throws IOException {

        Path sourceDir = Paths.get("/Users/Maxuemin/Desktop/englishout/上传/MP3");

        Path targetDir = Paths.get("/Users/Maxuemin/Desktop/englishout/上传/MP3new");

        if (!Files.exists(targetDir)) {
            Files.createDirectories(targetDir);
        }

        AtomicInteger counter = new AtomicInteger(0);
        AtomicInteger conflictCounter = new AtomicInteger(0);

        Files.walk(sourceDir)
                .filter(Files::isRegularFile)
                .filter(path -> path.toString().toLowerCase().endsWith(".mp3"))
                .forEach(source -> {
                    try {
                        Path target = targetDir.resolve(source.getFileName());

                        // 处理文件名冲突
                        int conflictIndex = 0;
                        while (Files.exists(target)) {
                            conflictIndex++;
                            String newName = insertSuffixBeforeExtension(
                                    source.getFileName().toString(),
                                    "_" + conflictIndex
                            );
                            target = targetDir.resolve(newName);
                            conflictCounter.incrementAndGet();
                        }

                        Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
                        counter.incrementAndGet();

                    } catch (IOException e) {
                        System.err.println("复制失败: " + source);
                        e.printStackTrace();
                    }
                });

        System.out.println("\n操作完成！");
        System.out.println("成功复制文件数: " + counter.get());
        System.out.println("自动解决冲突数: " + conflictCounter.get());
    }

    private static String insertSuffixBeforeExtension(String filename, String suffix) {
        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex == -1) return filename + suffix;
        return filename.substring(0, dotIndex) + suffix + filename.substring(dotIndex);
    }
}