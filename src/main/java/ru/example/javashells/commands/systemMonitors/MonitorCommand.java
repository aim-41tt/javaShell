package ru.example.javashells.commands.systemMonitors;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.ThreadMXBean;
import java.util.Arrays;

import ru.example.javashells.interfaces.Command;

public class MonitorCommand implements Command {

    @Override
    public String getName() {
        return "monitor";
    }

    @Override
    public void execute(String[] args) {
        StringBuilder sb = new StringBuilder();

        sb.append("Мониторинг системных ресурсов:\n\n");
        appendSystemInfo(sb);
        appendCpuUsage(sb);
        appendMemoryUsage(sb);
        appendDiskUsage(sb);

        System.out.println(sb.toString());
    }

    private void appendSystemInfo(StringBuilder sb) {
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
        
        sb.append("Информация о системе:\n")
          .append("  ОС: ").append(osBean.getName()).append(", ").append(osBean.getVersion()).append("\n")
          .append("  Архитектура: ").append(osBean.getArch()).append("\n")
          .append("  Количество процессоров (ядер): ").append(threadBean.getThreadCount()).append("\n")
          .append("  Количество потоков: ").append(osBean.getAvailableProcessors()).append("\n\n");
        
    }

    @SuppressWarnings("deprecation")
	private void appendCpuUsage(StringBuilder sb) {
        com.sun.management.OperatingSystemMXBean osBean = 
            (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

        double systemCpuLoad = osBean.getSystemCpuLoad();
        double processCpuLoad = osBean.getProcessCpuLoad();

        sb.append("Использование ЦП:\n")
          .append("  Загрузка ЦП системы: ")
          .append(formatPercentage(systemCpuLoad)).append(" %\n")
          .append("  Загрузка ЦП процесса: ")
          .append(formatPercentage(processCpuLoad)).append(" %\n\n");
    }

    private void appendMemoryUsage(StringBuilder sb) {
        Runtime runtime = Runtime.getRuntime();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long maxMemory = runtime.maxMemory();
        long usedMemory = totalMemory - freeMemory;

        sb.append("Использование памяти:\n")
          .append("  Использовано памяти: ").append(bytesToMB(usedMemory)).append(" МБ\n")
          .append("  Свободно памяти: ").append(bytesToMB(freeMemory)).append(" МБ\n")
          .append("  Общий объем памяти: ").append(bytesToMB(totalMemory)).append(" МБ\n")
          .append("  Максимальный объем памяти: ").append(bytesToMB(maxMemory)).append(" МБ\n\n");
    }

    private void appendDiskUsage(StringBuilder sb) {
        File[] roots = File.listRoots();

        sb.append("Использование диска:\n");
        Arrays.stream(roots).forEach(root -> {
            long totalSpace = root.getTotalSpace();
            long freeSpace = root.getFreeSpace();
            long usableSpace = root.getUsableSpace();

            sb.append("  Корень файловой системы: ").append(root.getAbsolutePath()).append("\n")
              .append("    Общий объем диска: ").append(bytesToGB(totalSpace)).append(" ГБ\n")
              .append("    Свободное дисковое пространство: ").append(bytesToGB(freeSpace)).append(" ГБ\n")
              .append("    Доступное дисковое пространство: ").append(bytesToGB(usableSpace)).append(" ГБ\n");
        });
        sb.append("\n");
    }

    private long bytesToMB(long bytes) {
        return bytes / (1024 * 1024);
    }

    private long bytesToGB(long bytes) {
        return bytes / (1024 * 1024 * 1024);
    }
    
    
    private String formatPercentage(double value) {
        if (value < 0) {
            return "Недоступно";
        }
        return String.format("%.2f", value * 100);
    }
    
}