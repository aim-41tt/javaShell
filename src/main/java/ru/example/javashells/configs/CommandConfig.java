package ru.example.javashells.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.example.javashells.commands.CdCommand;
import ru.example.javashells.commands.CrdirCommand;
import ru.example.javashells.commands.ExitCommand;
import ru.example.javashells.commands.HelpCommand;
import ru.example.javashells.commands.LsCommand;
import ru.example.javashells.components.managers.DirectoryManager;
import ru.example.javashells.interfaces.Command;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class CommandConfig {

	@Bean
	public DirectoryManager directoryManager() {
		return new DirectoryManager(new File(System.getProperty("user.dir")));
	}

	@Bean
	public Map<String, Command> commandMap(DirectoryManager directoryManager) {

		CrdirCommand crdirCommand = new CrdirCommand(directoryManager);
		CdCommand cdCommand = new CdCommand(directoryManager);
		LsCommand lsCommand = new LsCommand(directoryManager);

		Map<String, Command> commandMap = new HashMap<>();
		commandMap.put("cd", cdCommand);
		commandMap.put("crdir", crdirCommand);
		commandMap.put("ls", lsCommand);
		
		
		commandMap.put("exit", new ExitCommand());
		commandMap.put("help", new HelpCommand());
		return commandMap;
	}
}
