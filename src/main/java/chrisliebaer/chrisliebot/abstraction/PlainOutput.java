package chrisliebaer.chrisliebot.abstraction;

import lombok.NonNull;

public interface PlainOutput {
	
	public PlainOutput append(String s, Object... format);
	
	public PlainOutput appendEscape(String s, Object... format);
	
	public PlainOutput newLine();
	
	public PlainOutput clear();
	
	public default JoinPlainOutput joiner(String delimiter) {
		return new JoinPlainOutput(this, delimiter);
	}
	
	public interface PlainOutputSubstituion extends PlainOutput {
		
		public PlainOutputSubstituion appendSub(String s, Object... format);
		
		public PlainOutputSubstituion appendEscapeSub(String s, Object... format);
		
		@Override
		public PlainOutputSubstituion append(String s, Object... format);
		
		@Override
		public PlainOutputSubstituion appendEscape(String s, Object... format);
		
		@Override
		public PlainOutputSubstituion newLine();
		
		@Override
		public PlainOutputSubstituion clear();
	}
	
	@SuppressWarnings("ClassReferencesSubclass")
	public static PlainOutputSubstituion dummy() {
		return PlainOutputDummy.DUMMY;
	}
	
	public static final class PlainOutputDummy implements PlainOutputSubstituion {
		
		private static final PlainOutputDummy DUMMY = new PlainOutputDummy();
		
		private PlainOutputDummy() { }
		
		@Override
		public PlainOutputSubstituion appendSub(String s, Object... format) { return this; }
		
		@Override
		public PlainOutputSubstituion appendEscapeSub(String s, Object... format) { return this; }
		
		@Override
		public PlainOutputSubstituion append(String s, Object... format) { return this; }
		
		@Override
		public PlainOutputSubstituion appendEscape(String s, Object... format) { return this; }
		
		@Override
		public PlainOutputSubstituion newLine() { return this; }
		
		@Override
		public PlainOutputSubstituion clear() { return this; }
	}
	
	public static final class JoinPlainOutput implements PlainOutput {
		
		private @NonNull PlainOutput out;
		private @NonNull String delimiter;
		
		private boolean pending;
		
		public JoinPlainOutput(@NonNull PlainOutput out, @NonNull String delimiter) {
			this.out = out;
			this.delimiter = delimiter;
		}
		
		@Override
		public JoinPlainOutput append(String s, Object... format) {
			seperator();
			out.append(s, format);
			pending = true;
			return this;
		}
		
		@Override
		public JoinPlainOutput appendEscape(String s, Object... format) {
			seperator();
			out.appendEscape(s, format);
			pending = true;
			return this;
		}
		
		@Override
		public JoinPlainOutput newLine() {
			seperator();
			out.newLine();
			return this;
		}
		
		@Override
		public JoinPlainOutput clear() {
			pending = false;
			out.clear();
			return this;
		}
		
		private void seperator() {
			if (pending) {
				pending = false;
				out.appendEscape(delimiter);
			}
		}
	}
}
