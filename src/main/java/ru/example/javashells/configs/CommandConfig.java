package ru.example.javashells.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.example.javashells.commands.ClsCommand;
import ru.example.javashells.commands.ExitCommand;
import ru.example.javashells.commands.HelpCommand;
import ru.example.javashells.commands.catalogues.CdCommand;
import ru.example.javashells.commands.catalogues.CrFileCommand;
import ru.example.javashells.commands.catalogues.CrdirCommand;
import ru.example.javashells.commands.catalogues.LsCommand;
import ru.example.javashells.commands.systemMonitors.MonitorCommand;
import ru.example.javashells.commands.utilsForFilesAndDir.CopyCommand;
import ru.example.javashells.commands.utilsForFilesAndDir.DelCommand;
import ru.example.javashells.commands.utilsForFilesAndDir.RenameCommand;
import ru.example.javashells.components.managers.DirectoryManager;
import ru.example.javashells.interfaces.Command;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class CommandConfig {

	@Bean
	DirectoryManager directoryManager() {
		return new DirectoryManager(new File(System.getProperty("user.dir")));
	}
	
	@Bean
	ExecutorService executorService() {
		return Executors.newCachedThreadPool();
	}
	

	@Bean
	Map<String, Command> commandMap(DirectoryManager directoryManager, ExecutorService executorService) {
		Map<String, Command> commandMap = new HashMap<>();

		registerCommand(commandMap, new CdCommand(directoryManager));
		registerCommand(commandMap, new CrdirCommand(directoryManager, executorService));
		registerCommand(commandMap, new LsCommand(directoryManager, executorService));
		registerCommand(commandMap, new CrFileCommand(directoryManager, executorService));
		registerCommand(commandMap, new DelCommand(directoryManager));
		registerCommand(commandMap, new CopyCommand(directoryManager));
		registerCommand(commandMap, new RenameCommand(directoryManager));
		
		registerCommand(commandMap, new MonitorCommand(executorService));

		registerCommand(commandMap, new ClsCommand());
		registerCommand(commandMap,new ExitCommand());
		registerCommand(commandMap, new HelpCommand());
		
		return commandMap;
	}

	private void registerCommand(Map<String, Command> commandMap, Command command) {
		commandMap.put(command.getName(), command);
	}

}
