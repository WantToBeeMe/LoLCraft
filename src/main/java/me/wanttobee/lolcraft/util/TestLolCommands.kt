package me.wanttobee.lolcraft.util

import me.wanttobee.commandtree.Description
import me.wanttobee.commandtree.ITreeCommand
import me.wanttobee.commandtree.partials.BooleanPartial
import me.wanttobee.commandtree.partials.BranchPartial
import me.wanttobee.commandtree.partials.EmptyPartial
import me.wanttobee.commandtree.partials.ICommandPartial
import me.wanttobee.lolcraft.Test
import me.wanttobee.lolcraft.base.players.PlayerContextSystem

object TestLolCommands : ITreeCommand {
    override val description: Description = Description("All commands to test commands for the league of legends plugin")
        .addSubDescription("settings", "Opens your settings window", "/testlol")

    override val command: ICommandPartial = BranchPartial("testlol").setStaticPartials(
        BooleanPartial("silence").setEffect { invoker, value -> Test.setSilence(invoker, value) },
        BooleanPartial("stun").setEffect { invoker, value -> Test.setStun(invoker, value) },
        EmptyPartial("abilities").setEffect { invoker -> Test.setAbilities(invoker) } ,
        EmptyPartial("otherThing").setEffect { invoker -> Test.otherThing(invoker) }
    )
}