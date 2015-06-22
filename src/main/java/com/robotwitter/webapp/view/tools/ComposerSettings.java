package com.robotwitter.webapp.view.tools;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.robotwitter.database.primitives.DBScheduledTweet;
import com.robotwitter.posting.TweetPostingPreferenceType;
import com.robotwitter.webapp.control.tools.ITweetingController;
import com.robotwitter.webapp.messages.IMessagesContainer;
import com.robotwitter.webapp.util.AbstractFormComponent;
import com.robotwitter.webapp.util.AbstractTextFieldValidator;
import com.robotwitter.webapp.util.RobotwitterCustomComponent;
import com.robotwitter.webapp.util.WindowWithDescription;
import com.robotwitter.webapp.view.tools.TweetComposer.CharacterCount;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.validator.AbstractStringValidator;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.server.UserError;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Select;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;

/**
 * Represents a window with the different settings for the Tweet Composer
 * 
 * @author Amir Drutin
 *
 */
public class ComposerSettings extends RobotwitterCustomComponent {
	
	/** Represents a validator of a Tweet. */
	static class SuffixPrefixValidator extends AbstractStringValidator
	{

		/**
		 * Instantiates a new Suffix/Prefix validator.
		 *
		 * @param tooLongError
		 *            the error message to display when the Suffix/Prefix is too long
		 * @param count
		 *            the character count that is updated outside
		 * @param max
		 *            the maximum allowed character count
		 */
		public SuffixPrefixValidator(
			String tooLongError,
			int count,
			int max)
		{
			super(tooLongError);
			this.count = count;
			this.max = max;
		}
		
		
		
		protected final boolean isValidValue(final String input)
		{
			if (input.isEmpty()) { return true; }

			if (count > max) { return false; }
			
			return true;
		}



		/** The current character count. */
		int count;
		
		/** The maximum allowed character count. */
		int max;
		
		/** Serialisation version unique ID. */
		private static final long serialVersionUID = 1L;
	}

	public ComposerSettings(IMessagesContainer messages,
			ITweetingController controller) {
		super(messages);
		this.tweetingController = controller;
		
		initialiseLayout();
		
	}
	
	private void initialiseSelect(){
		HashMap<TweetPostingPreferenceType, String> preferences = new HashMap<TweetPostingPreferenceType, String>();
		preferences.put(TweetPostingPreferenceType.BASIC,
				messages.get("ComposerSettings.select-box.basic"));
		preferences.put(TweetPostingPreferenceType.NUMBERED,
				messages.get("ComposerSettings.select-box.numbered"));
		preferences.put(TweetPostingPreferenceType.PREFIX,
				messages.get("ComposerSettings.select-box.prefix"));
		preferences.put(TweetPostingPreferenceType.POSTFIX,
				messages.get("ComposerSettings.select-box.suffix"));

		select = new Select(messages.get("ComposerSettings.caption.select-box"));
		for (Iterator<TweetPostingPreferenceType> i = preferences.keySet()
				.iterator(); i.hasNext();) {
			TweetPostingPreferenceType key = i.next();
			select.addItem(key);
			select.setItemCaption(key, preferences.get(key));
		}

		select.setValue(TweetPostingPreferenceType.BASIC);
		select.addValueChangeListener(new Property.ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				TweetPostingPreferenceType type = (TweetPostingPreferenceType) select.getValue();
				tweetingController.setPreference(type);
			}
		});
		/**select.addListener(new ItemClickEvent.ItemClickListener(){
			@Override
            public void itemClick(ItemClickEvent event) {
				tweetingController.setPreference(select.getValue());
            }
		}*/
	}
	
	private void updatePrefix(String input){
		tweetingController.setPrefix(input);
		prefix.removeAllValidators();
		prefix.addValidator(new SuffixPrefixValidator(messages.get("ComposerSettings.error.prefix-too-long"), input.length(), max));
	}
	
	private void initialisePrefix(){
		prefix = new TextArea();
		prefix.setRows(1);
		prefix.addTextChangeListener(event -> updatePrefix(event.getText()));
		prefix.setValidationVisible(false);
		
		errorMessage = new Label();
		errorMessage.setVisible(false);
	}
	
	private void initialiseSuffix(){
		suffix = new TextArea();
		suffix.setRows(1);
		suffix.addTextChangeListener(event -> updatePrefix(event.getText()));
		suffix.setValidationVisible(false);
		
		errorMessage = new Label();
		errorMessage.setVisible(false);
	}
	
	private void initialiseLayout(){
		initialiseSelect();
		initialiseSuffix();
		initialisePrefix();
		
		layout = new VerticalLayout();
	}
	
	/** Clears the displayed error message on the suffix/prefix box. */
	private void clearErrorMessage(TextArea text)
	{
		errorMessage.setVisible(false);
		text.setComponentError(null);
		text.setValidationVisible(false);
	}
	
	/**
	 * Displays an error message on the suffix/prefix box.
	 *
	 * @param error
	 *            the error message to display
	 * @param text
	 * 			  the text area with the error.
	 */
	private void setErrorMessage(String error, TextArea text)
	{
		// Clear any previous error message.
		clearErrorMessage(text);
		
		// Set the error message
		errorMessage.setVisible(true);
		errorMessage.setValue(error);
		
		// Set the error message on the field
		text.setValidationVisible(true);
		text.setCursorPosition(errorMessage.getValue().length());
		if (text.getErrorMessage() == null)
		{
			text.setComponentError(new UserError(error));
		}
	}
	
	
	/** The error message of a non valid suffix/prefix input. */
	private Label errorMessage;
	
	/** The custom suffix input text area. */
	private Select select;
	
	/** The custom prefix input text area. */
	private TextArea prefix;
	
	/** The custom suffix input text area. */
	private TextArea suffix;
	
	/** The setting's layout. */
	private VerticalLayout layout;

	/** The current character count of the prefix/suffix. */
	int count;
	
	/** The maximum length of a custom suffix/prefix. */
	int max = 50;
	
	/** The tweeting controller. */
	private ITweetingController tweetingController;
}