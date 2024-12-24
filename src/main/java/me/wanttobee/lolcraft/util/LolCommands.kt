package me.wanttobee.lolcraft.util

import me.wanttobee.commandtree.Description
import me.wanttobee.commandtree.ITreeCommand
import me.wanttobee.commandtree.partials.BranchPartial
import me.wanttobee.commandtree.partials.EmptyPartial
import me.wanttobee.commandtree.partials.ICommandPartial
import me.wanttobee.lolcraft.Abilities

object LolCommands : ITreeCommand {
    override val description: Description = Description("Commands for the League of Legends plugin")

    override val command: ICommandPartial = BranchPartial("lol").setStaticPartials(
        EmptyPartial("test").setEffect { invoker -> Abilities.setAbilities(invoker) }
    )
}