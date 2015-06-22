package com.robotwitter.posting;

public enum AutomateTweetPostingPeriod {
	SINGLE {
		@Override
		public int getPeriod() {
			return 0;
		}
	},
	
	DAILY {
		@Override
		public int getPeriod() {
			return 1000 * 60 * 60 * 24;
		}
	}, 
	
	WEEKLY {
		@Override
		public int getPeriod() {
			return 1000 * 60 * 60 * 24 * 7;
		}
	}, 
	
	MONTHLY {
		@Override
		public int getPeriod() {
			return 1000 * 60 * 60 * 30;
		}
	};
	
	public abstract int getPeriod();
}
