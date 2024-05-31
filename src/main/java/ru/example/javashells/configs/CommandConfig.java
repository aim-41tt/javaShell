package ru.example.javashells.configs;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.example.javashells.commands.CdCommand;
import ru.example.javashells.commands.CrdirCommand;
import ru.example.javashells.commands.ExitCommand;
import ru.example.javashells.interfaces.Command;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class CommandConfig {

    @Bean
    public Map<String, Command> commandMap() {
        Map<String, Command> commandMap = new HashMap<>();
        commandMap.put("cd", new CdCommand(new File(System.getProperty("user.dir"))));
        commandMap.put("exit", new ExitCommand());
        commandMap.put("crdir", new CrdirCommand());
        // Добавьте другие команды здесь
        return commandMap;
    }
}
