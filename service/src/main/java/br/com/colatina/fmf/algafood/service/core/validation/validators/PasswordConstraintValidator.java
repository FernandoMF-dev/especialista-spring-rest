package br.com.colatina.fmf.algafood.service.core.validation.validators;

import br.com.colatina.fmf.algafood.service.core.validation.constraints.Password;
import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.Rule;
import org.passay.RuleResult;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class PasswordConstraintValidator implements ConstraintValidator<Password, String> {
	private Password constraint;

	@Override
	public void initialize(Password constraint) {
		this.constraint = constraint;
	}

	@Override
	public boolean isValid(String password, ConstraintValidatorContext context) {
		List<Rule> rules = new ArrayList<>();

		addCharacterDataRule(rules, EnglishCharacterData.LowerCase, constraint.lowerCase());
		addCharacterDataRule(rules, EnglishCharacterData.UpperCase, constraint.upperCase());
		addCharacterDataRule(rules, EnglishCharacterData.Digit, constraint.numerical());
		addCharacterDataRule(rules, EnglishCharacterData.Special, constraint.special());
		addLengthRule(rules);

		PasswordValidator passwordValidator = new PasswordValidator(rules);
		PasswordData passwordData = new PasswordData(password);
		RuleResult result = passwordValidator.validate(passwordData);

		return result.isValid();
	}

	private void addLengthRule(List<Rule> rules) {
		rules.add(new LengthRule(this.constraint.minLenght(), this.constraint.maxLenght()));
	}

	private void addCharacterDataRule(List<Rule> list, CharacterData characterData, int value) {
		if (value > 0) {
			list.add(new CharacterRule(characterData, value));
		}
	}
}
