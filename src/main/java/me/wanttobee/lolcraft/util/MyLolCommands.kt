package me.wanttobee.lolcraft.util

import me.wanttobee.commandtree.Description
import me.wanttobee.commandtree.ITreeCommand
import me.wanttobee.commandtree.partials.BranchPartial
import me.wanttobee.commandtree.partials.EmptyPartial
import me.wanttobee.commandtree.partials.ICommandPartial
import me.wanttobee.lolcraft.base.players.PlayerContextSystem

object MyLolCommands : ITreeCommand {
    override val description: Description = Description("All commands that are personal to the player playing it right now")
        .addSubDescription("settings", "Opens your settings window", "/mylol settings")

    override val command: ICommandPartial = BranchPartial("mylol").setStaticPartials(
        EmptyPartial("settings").setEffect { invoker -> PlayerContextSystem.getContext(invoker).settings.openSettings() }
    )
}