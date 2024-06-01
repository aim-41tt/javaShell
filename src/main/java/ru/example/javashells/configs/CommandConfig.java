package ru.example.javashells.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.example.javashells.commands.CdCommand;
import ru.example.javashells.commands.CrdirCommand;
import ru.example.javashells.commands.ExitCommand;
import ru.example.javashells.commands.HelpCommand;
import ru.example.javashells.interfaces.Command;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class CommandConfig {

	@Bean
	public Map<String, Command> commandMap() {

		File initialDirectory = new File(System.getProperty("user.dir"));

		CrdirCommand crdirCommand = new CrdirCommand(initialDirectory);
		CdCommand cdCommand = new CdCommand(initialDirectory, crdirCommand);

		Map<String, Command> commandMap = new HashMap<>();
		commandMap.put("cd", cdCommand);
		commandMap.put("exit", new ExitCommand());
		commandMap.put("crdir", crdirCommand);
		commandMap.put("help", new HelpCommand());
		return commandMap;
	}
}
