package chrisliebaer.chrisliebot.command.bottlespin;

import chrisliebaer.chrisliebot.abstraction.ChrislieChannel;
import chrisliebaer.chrisliebot.abstraction.ChrislieFormat;
import chrisliebaer.chrisliebot.abstraction.ChrislieMessage;
import chrisliebaer.chrisliebot.command.ChrisieCommand;

import java.util.concurrent.ThreadLocalRandom;

public class BottleSpinCommand implements ChrisieCommand {
	
	@Override
	public void execute(ChrislieMessage m, String arg) {
		var rng = ThreadLocalRandom.current();
		if (m.channel().isDirectMessage()) {
			String choice = rng.nextBoolean()? "mich" : "dich";
			
			m.reply()
					.title("Die Flasche hat entschieden")
					.description(out -> out.appendEscape(choice))
					.replace(out -> out
							.appendEscape("Die Flasche hat ")
							.appendEscape(choice, ChrislieFormat.HIGHLIGHT)
							.appendEscape(" ausgewählt."))
					.send();
		} else {
			ChrislieChannel chan = m.channel();
			var userList = chan.users();
			var user = userList.get(rng.nextInt(userList.size()));
			
			m.reply()
					.title("Die Flasche hat entschieden")
					.description(out -> out.appendEscape(user.mention()))
					.replace(out -> out
							.appendEscape("Die Flasche hat ")
							.appendEscape(user.displayName(), ChrislieFormat.HIGHLIGHT)
							.appendEscape(" ausgewählt."))
					.send();
		}
	}
}
