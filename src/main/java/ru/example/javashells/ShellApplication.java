package ru.example.javashells;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import ru.example.javashells.commands.CdCommand;
import ru.example.javashells.interfaces.Command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.StringTokenizer;

@SpringBootApplication
@Import(ru.example.javashells.configs.CommandConfig.class)
public class ShellApplication {

	private String directori;
	
    public static void main(String[] args) {
        SpringApplication.run(ShellApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(Map<String, Command> commandMap) {
        return args -> {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String commandLine;

            CdCommand cdCommand = (CdCommand) commandMap.get("cd");

            System.out.println("");

            while (true) {
                System.out.print(cdCommand.getCurrentDirectory().getAbsolutePath() + "\\$>~ ");
                commandLine = reader.readLine();

                StringTokenizer tokenizer = new StringTokenizer(commandLine);
                if (!tokenizer.hasMoreTokens()) {
                    continue;
                }

                String commandName = tokenizer.nextToken();
                Command command = commandMap.get(commandName);

                if (command != null) {
                    String[] cmdArray = new String[tokenizer.countTokens() + 1];
                    cmdArray[0] = commandName;
                    int index = 1;
                    while (tokenizer.hasMoreTokens()) {
                        cmdArray[index++] = tokenizer.nextToken();
                    }
                    command.execute(cmdArray);
                } else {
                    // Если команда не встроенная, пробуем выполнить как системную
                    String[] cmdArray = new String[tokenizer.countTokens() + 1];
                    cmdArray[0] = commandName;
                    int index = 1;
                    while (tokenizer.hasMoreTokens()) {
                        cmdArray[index++] = tokenizer.nextToken();
                    }

                    ProcessBuilder pb = new ProcessBuilder(cmdArray);
                    try {
                        Process process = pb.start();
                        BufferedReader processOutput = new BufferedReader(new InputStreamReader(process.getInputStream()));
                        String line;
                        while ((line = processOutput.readLine()) != null) {
                            System.out.println(line);
                        }
                        process.waitFor();
                    } catch (IOException | InterruptedException e) {
                        System.out.println("Ошибка выполнения команды: " + e.getMessage());
                    }
                }
            }
        };
    }
}