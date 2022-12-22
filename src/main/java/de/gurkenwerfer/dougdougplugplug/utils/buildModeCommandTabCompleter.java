package de.gurkenwerfer.dougdougplugplug.utils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class buildModeCommandTabCompleter implements TabCompleter {
    List < String > results = new ArrayList < > ();
    @Override
    public List < String > onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        if (args.length == 1) {
            if (cmd.getLabel().equalsIgnoreCase("buildmode") || cmd.getLabel().equalsIgnoreCase("optional alias from plugin.yml")) {

                results.clear();
                results.add("add");
                results.add("remove");
                results.add("reload");

            }
        }
        return sortedResults(args[0]);
    }
    public List < String > sortedResults(String arg) {
        final List < String > completions = new ArrayList < > ();
        StringUtil.copyPartialMatches(arg, results, completions);
        Collections.sort(completions);
        results.clear();
        for (String s: completions) {
            results.add(s);
        }
        return results;
    }
}
