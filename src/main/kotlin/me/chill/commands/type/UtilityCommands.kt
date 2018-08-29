package me.chill.commands.type

import me.chill.commands.arguments.ArgumentType
import me.chill.commands.framework.*
import me.chill.utility.jda.embed
import me.chill.utility.settings.*
import net.dv8tion.jda.core.entities.MessageEmbed

@CommandCategory
fun utilityCommands() = commands {
	name = "Utility"
	command("ping") {
		execute {
			val jda = getJDA()
			val latency = jda.ping
			respond(pingEmbed(latency))
		}
	}

	command("invite") {
		execute {
			respond(inviteEmbed())
		}
	}

	command("source") {
		execute {
			respond(sourceEmbed())
		}
	}

	command("commands") {
		execute {
			respond(
				listCommandsEmbed(
					CommandContainer.commandSets,
					getJDA().selfUser.avatarUrl
				)
			)
		}
	}

	command("help") {
		expects(ArgumentType.Word)
		execute {
			val commandName = getArguments()[0] as String
			respond(commandInfoEmbed(CommandContainer.getCommand(commandName)))
		}
	}
}

private fun commandInfoEmbed(command: Command) =
	embed {
		title = "Help - ${command.name}"
		color = green
		field {
			title = "Syntax"
			description = "$command"
		}
	}

private fun listCommandsEmbed(commandSets: List<CommandSet>, avatarUrl: String) =
	embed {
		title = "Commands"
		color = green
		thumbnail = avatarUrl
		commandSets.forEach { set ->
			field {
				title = set.name
				description = set
					.getCommandNames()
					.joinToString("\n") { commandName ->
						"- $commandName"
					}
				inline = false
			}
		}
	}

private fun pingEmbed(latency: Long): MessageEmbed? {
	var color: Int? = null
	var thumbnail: String? = null

	when {
		latency < 30 -> {
			color = green
			thumbnail = happy
		}
		latency < 60 -> {
			color = yellow
			thumbnail = clap
		}
		latency > 90 -> {
			color = red
			thumbnail = noWay
		}
	}

	return embed {
		title = "Pong! \uD83C\uDFD3"
		description = "Ping took **${latency}ms**"
		this.color = pingEmbed@ color
		this.thumbnail = pingEmbed@ thumbnail
	}
}

private fun inviteEmbed() =
	embed {
		title = "Invite Taiga"
		description = "[Invite me](https://discordapp.com/oauth2/authorize?client_id=482340927709511682&scope=bot&permissions=8) to your server!"
		color = blue
		thumbnail = fondling
	}

private fun sourceEmbed() =
	embed {
		title = "Source Code"
		description =
			"- [GitHub repository](https://github.com/woojiahao/Taiga)\n" +
			"- [Wiki](https://github.com/woojiahao/Taiga/wiki)"
		color = blue
		thumbnail = serve
	}