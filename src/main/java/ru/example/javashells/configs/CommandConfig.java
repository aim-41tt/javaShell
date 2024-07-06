package ru.example.javashells.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.example.javashells.commands.ClsCommand;
import ru.example.javashells.commands.ExitCommand;
import ru.example.javashells.commands.HelpCommand;
import ru.example.javashells.commands.catalogues.CdCommand;
import ru.example.javashells.commands.catalogues.CopyCommand;
import ru.example.javashells.commands.catalogues.CrFileCommand;
import ru.example.javashells.commands.catalogues.CrdirCommand;
import ru.example.javashells.commands.catalogues.DelCommand;
import ru.example.javashells.commands.catalogues.LsCommand;
import ru.example.javashells.commands.catalogues.RenameCommand;
import ru.example.javashells.commands.systemMonitors.MonitorCommand;
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
		Map<String, Command> commandMap = new HashMap<>();

		registerCommand(commandMap, new CdCommand(directoryManager));
		registerCommand(commandMap, new CrdirCommand(directoryManager));
		registerCommand(commandMap, new LsCommand(directoryManager));
		registerCommand(commandMap, new CrFileCommand(directoryManager));
		registerCommand(commandMap, new DelCommand(directoryManager));
		registerCommand(commandMap, new CopyCommand(directoryManager));
		registerCommand(commandMap, new RenameCommand(directoryManager));
		
		registerCommand(commandMap, new MonitorCommand());

		registerCommand(commandMap, new ClsCommand());
		registerCommand(commandMap,new ExitCommand());
		registerCommand(commandMap, new HelpCommand());
		return commandMap;
	}

	private void registerCommand(Map<String, Command> commandMap, Command command) {
		commandMap.put(command.getName(), command);
	}

}
