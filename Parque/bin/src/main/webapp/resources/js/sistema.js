function showDialog(dialog){
	PF(dialog).show();
	return false;
}

function hideDialog(dialog){
	PF(dialog).hide();
	return false;
}

function validate(dialog, args) {
	if(!args.validationFailed){
		hideDialog(dialog);
	}
}