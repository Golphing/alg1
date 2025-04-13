package other.test;

import java.nio.file.*;
import java.util.Scanner;
import java.util.regex.Pattern;

public class FileNameFormatter {
    public static void main(String[] args) throws Exception {
        Path rootDir = Paths.get("/Users/Maxuemin/Desktop/englishout/上传");

        Files.walk(rootDir)
                .filter(Files::isRegularFile)
                .forEach(file -> {
                    try {
                        String fileName = file.getFileName().toString();
                        String newName = fileName.replace("_1", ""); // 删除所有_1

                        // 处理空文件名的情况
                        if (newName.trim().isEmpty()) {
                            newName = "untitled" + getExtension(fileName);
                        }

                        // 解决文件名冲突
                        Path target = file.resolveSibling(newName);
                        int count = 1;
                        if (Files.exists(target)) {
                            Files.delete(file);
                        }else {
// 执行重命名
                            Files.move(file, target);
                            System.out.println("已重命名: " + fileName + " → " + target.getFileName());
                        }


                    } catch (Exception e) {
                        System.err.println("处理失败: " + file);
                        e.printStackTrace();
                    }
                });

        System.out.println("处理完成！");
    }

    // 获取文件扩展名
    private static String getExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex > 0) ? fileName.substring(dotIndex) : "";
    }
}
