package com.dongluhitec.card;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class CommonUI {
	
	public static void error(String title,String text){
		Shell shell = Display.getDefault().getActiveShell();
		MessageBox messageBox =  new MessageBox(shell, SWT.OK|SWT.CANCEL|SWT.ICON_ERROR);
		messageBox.setText(title);
		messageBox.setMessage(text);
		messageBox.open();
	}
	
	public static void info(String title,String text){
		Shell shell = Display.getDefault().getActiveShell();
		MessageBox messageBox =  new MessageBox(shell, SWT.OK|SWT.CANCEL|SWT.ICON_INFORMATION);
		messageBox.setText(title);
		messageBox.setMessage(text);
		messageBox.open();
	}
}
