package com.talentframework.commandcenter;

import com.talentframework.talentexception.TalentException;

public interface CommandBefore {
	public Object before(Command cmd) throws TalentException;
}
