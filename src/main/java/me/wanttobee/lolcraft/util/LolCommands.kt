package me.wanttobee.lolcraft.util

import me.wanttobee.commandtree.Description
import me.wanttobee.commandtree.ITreeCommand
import me.wanttobee.commandtree.partials.BranchPartial
import me.wanttobee.commandtree.partials.ICommandPartial

object LolCommands : ITreeCommand {
    override val description: Description = Description("Commands for the League of Legends plugin")

    override val command: ICommandPartial = BranchPartial("lol").setStaticPartials(
        //EmptyPartial("test").setEffect { invoker -> Test.setAbilities(invoker) }
    )
}