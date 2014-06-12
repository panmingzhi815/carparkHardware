package com.dongluhitec.card.client;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class ClientUI {
	private ClientPresenter presenter;
	protected Shell shlv;
	private Text text_outsideScreenText;
	private Text text_insideScreenText;
	private Text text_2;
	private Text text_3;
	private Button rb_up;
	private Button rb_down;
	private Button rb_stop;
	private Button button_insideScreenOpen;
	private Button button_insideScreenClose;
	private Button button_outsideVoiceOpen;
	private Button button_outsideVoiceClose;
	private Button button_insideVoiceOpen;
	private Button button_outsideScreenOpen;
	private Button button_outsideScreenClose;
	private Button button_insideVoiceClose;
	private Button rb_false;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ClientUI window = new ClientUI();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlv.open();
		shlv.layout();
		presenter = new ClientPresenter(this);
		while (!shlv.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		presenter.close();
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlv = new Shell();
		shlv.setSize(450, 530);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		shlv.setText("东陆高新停车场底层客户端v"+sdf.format(new Date()));
		
		Group group = new Group(shlv, SWT.NONE);
		group.setText("操作");
		group.setBounds(10, 0, 422, 215);
		
		Composite composite = new Composite(group, SWT.BORDER);
		composite.setBounds(10, 56, 189, 24);
		
		Label lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setBounds(10, 6, 50, 12);
		lblNewLabel.setText("内置语音");
		
		button_insideVoiceOpen = new Button(composite, SWT.RADIO);
		button_insideVoiceOpen.setBounds(63, 4, 45, 16);
		button_insideVoiceOpen.setText("打开");
		button_insideVoiceOpen.setSelection(true);
		
		button_insideVoiceClose = new Button(composite, SWT.RADIO);
		button_insideVoiceClose.setBounds(113, 4, 45, 16);
		button_insideVoiceClose.setText("关闭");
		
		Composite composite_1 = new Composite(group, SWT.BORDER);
		composite_1.setBounds(10, 89, 189, 24);
		
		Label label = new Label(composite_1, SWT.NONE);
		label.setText("外置语音");
		label.setBounds(10, 6, 50, 12);
		
		button_outsideVoiceOpen = new Button(composite_1, SWT.RADIO);
		button_outsideVoiceOpen.setText("打开");
		button_outsideVoiceOpen.setBounds(63, 4, 45, 16);
		button_outsideVoiceOpen.setSelection(true);
		
		button_outsideVoiceClose = new Button(composite_1, SWT.RADIO);
		button_outsideVoiceClose.setText("关闭");
		button_outsideVoiceClose.setBounds(113, 4, 45, 16);
		
		Composite composite_2 = new Composite(group, SWT.BORDER);
		composite_2.setBounds(211, 56, 189, 24);
		
		Label label_1 = new Label(composite_2, SWT.NONE);
		label_1.setText("内置屏显");
		label_1.setBounds(10, 6, 67, 12);
		
		button_insideScreenOpen = new Button(composite_2, SWT.RADIO);
		button_insideScreenOpen.setText("打开");
		button_insideScreenOpen.setBounds(83, 4, 45, 16);
		button_insideScreenOpen.setSelection(true);
		
		button_insideScreenClose = new Button(composite_2, SWT.RADIO);
		button_insideScreenClose.setText("关闭");
		button_insideScreenClose.setBounds(134, 4, 45, 16);
		
		Composite composite_3 = new Composite(group, SWT.BORDER);
		composite_3.setBounds(211, 89, 189, 24);
		
		Label label_2 = new Label(composite_3, SWT.NONE);
		label_2.setText("外置屏显");
		label_2.setBounds(10, 6, 67, 12);
		
		button_outsideScreenOpen = new Button(composite_3, SWT.RADIO);
		button_outsideScreenOpen.setText("打开");
		button_outsideScreenOpen.setBounds(83, 4, 45, 16);
		button_outsideScreenOpen.setSelection(true);
		
		button_outsideScreenClose = new Button(composite_3, SWT.RADIO);
		button_outsideScreenClose.setText("关闭");
		button_outsideScreenClose.setBounds(134, 4, 45, 16);
		
		Composite composite_4 = new Composite(group, SWT.BORDER);
		composite_4.setBounds(10, 26, 390, 24);
		
		Label label_3 = new Label(composite_4, SWT.NONE);
		label_3.setAlignment(SWT.RIGHT);
		label_3.setBounds(17, 4, 39, 12);
		label_3.setText("\u95F8\u673A");
		
		rb_up = new Button(composite_4, SWT.RADIO);
		rb_up.setBounds(63, 2, 39, 16);
		rb_up.setText("起");
		rb_up.setSelection(true);
		
		rb_down = new Button(composite_4, SWT.RADIO);
		rb_down.setText("落");
		rb_down.setBounds(113, 2, 39, 16);
		
		rb_stop = new Button(composite_4, SWT.RADIO);
		rb_stop.setText("停");
		rb_stop.setBounds(158, 2, 39, 16);
		
		rb_false = new Button(composite_4, SWT.RADIO);
		rb_false.setBounds(200, 2, 39, 16);
		rb_false.setText("无效");
		
		Composite composite_5 = new Composite(group, SWT.BORDER);
		composite_5.setBounds(10, 155, 390, 30);
		
		Label label_4 = new Label(composite_5, SWT.NONE);
		label_4.setBounds(10, 6, 93, 12);
		label_4.setText("外置显示屏内容:");
		
		text_outsideScreenText = new Text(composite_5, SWT.BORDER);
		text_outsideScreenText.setBounds(109, 3, 271, 18);
		text_outsideScreenText.setText("这是外置显示屏的内容");
		
		Composite composite_6 = new Composite(group, SWT.BORDER);
		composite_6.setBounds(10, 119, 390, 30);
		
		Label label_5 = new Label(composite_6, SWT.NONE);
		label_5.setText("内置显示屏内容:");
		label_5.setBounds(10, 6, 93, 12);
		
		text_insideScreenText = new Text(composite_6, SWT.BORDER);
		text_insideScreenText.setBounds(109, 3, 271, 18);
		text_insideScreenText.setText("这是内置显示屏的内容");
		
		Button btnNewButton = new Button(group, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				presenter.sendDeviceControl();
			}
		});
		btnNewButton.setBounds(339, 191, 72, 22);
		btnNewButton.setText("发送");
		
		Group group_1 = new Group(shlv, SWT.NONE);
		group_1.setText("密文");
		group_1.setBounds(10, 221, 220, 235);
		
		text_2 = new Text(group_1, SWT.BORDER | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		text_2.setBounds(10, 27, 201, 198);
		
		Group group_2 = new Group(shlv, SWT.NONE);
		group_2.setText("明文");
		group_2.setBounds(236, 221, 196, 235);
		
		text_3 = new Text(group_2, SWT.BORDER | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		text_3.setBounds(10, 24, 180, 201);
		
		Button button_12 = new Button(shlv, SWT.NONE);
		button_12.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				text_2.setText("");
				text_3.setText("");
			}
		});
		button_12.setBounds(200, 462, 72, 22);
		button_12.setText("\u6E05\u7A7A");
	}
	
	public void println(final String text){
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				if(text_3.getText().length() > 5000){
					text_3.setText("");
				}
				text_3.append("\n"+text+"\n");
			}
		});
	}
	
	public void println_encode(final String text){
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				if(text_2.getText().length() > 5000){
					text_2.setText("");
				}
				text_2.append("\n"+text+"\n");
			}
		});
	}
	
	public Document state2Xml(){
		Document document = DocumentHelper.createDocument();
		Element rootElement = document.addElement("dongluCarpark");
		Element controlElement = rootElement.addElement("control");
		
		String gate = "";
		if(rb_up.getSelection() == true){
			gate = "up";
		}
		if(rb_down.getSelection() == true){
			gate = "down";
		}
		if(rb_stop.getSelection() == true){
			gate = "stop";
		}
		if(rb_false.getSelection() == true){
			gate = "false";
		}
		controlElement.addElement("gate").setText(gate);
		
		String insideVoiceOpen = button_insideVoiceOpen.getSelection() == true ? "true":"false";
		controlElement.addElement("insideVoice").setText(insideVoiceOpen);
		
		String outsideVoiceOpen = button_outsideVoiceOpen.getSelection() == true ? "true":"false";
		controlElement.addElement("outsideVoice").setText(outsideVoiceOpen);
		
		String insideScreenOpen = button_insideScreenOpen.getSelection() == true ? "true":"false";
		controlElement.addElement("insideScreen").setText(insideScreenOpen);
		
		String outsideScreenOpen = button_outsideScreenOpen.getSelection() == true ? "true":"false";
		controlElement.addElement("outsideScreen").setText(outsideScreenOpen);
		
		String insideScreenText = text_insideScreenText.getText().trim();
		controlElement.addElement("insideScreenAndVoiceData").setText(insideScreenText);
		
		String outsideScreenText = text_outsideScreenText.getText().trim();
		controlElement.addElement("outsideScreenAndVoiceData").setText(outsideScreenText);
		
		return document;
	}
}
