package com.dongluhitec.card.server;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.dom4j.Document;
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

public class ServerUI {

	private ServerPresenter presenter;
	protected Shell shell;
	private Text txt_cardNO;
	private Text text_1;
	private Text text_2;
	private Text text_insideScreenText;
	private Text text_outsideScreenText;
	private Text txt_ip;
	private Text txt_port;
	private Text txt_deviceName;
	private Button rb_deviceInOutType_in;
	private Button rb_deviceInOutType_out;
	private Button rb_deviceDisplayAndVoiceInside_true;
	private Button rb_deviceDisplayAndVoiceInside_false;
	private Button rb_deviceDisplayAndVoiceOutside_true;
	private Button rb_deviceDisplayAndVoiceOutside_false;
	private Button rb_deviceDisplaySupportChinese_true;
	private Button rb_deviceDisplaySupportChinese_false;
	private Button rb_up;
	private Button rb_down;
	private Button rb_stop;
	private Button button_insideVoiceOpen;
	private Button button_insideVoiceClose;
	private Button button_insideScreenOpen;
	private Button button_insideScreenClose;
	private Button button_outsideScreenOpen;
	private Button button_outsideScreenClose;
	private Button button_outsideVoiceOpen;
	private Button button_outsideVoiceClose;
	private Button rb_false;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ServerUI window = new ServerUI();
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
		shell.open();
		shell.layout();
		presenter = new ServerPresenter(this);
		while (!shell.isDisposed()) {
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
		shell = new Shell();
		shell.setSize(529, 670);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		shell.setText("东陆高新停车场底层服务器v"+sdf.format(new Date()));
		
		Group group_1 = new Group(shell, SWT.NONE);
		group_1.setText("密文");
		group_1.setBounds(10, 426, 255, 179);
		
		text_1 = new Text(group_1, SWT.BORDER | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		text_1.setBounds(10, 20, 235, 156);
		
		Group group_2 = new Group(shell, SWT.NONE);
		group_2.setText("译文");
		group_2.setBounds(271, 426, 240, 179);
		
		text_2 = new Text(group_2, SWT.BORDER | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		text_2.setBounds(10, 20, 220, 156);
		
		Button button_3 = new Button(shell, SWT.NONE);
		button_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				text_1.setText("");
				text_2.setText("");
			}
		});
		button_3.setBounds(243, 611, 72, 22);
		button_3.setText("清空");
		
		Group group_3 = new Group(shell, SWT.NONE);
		group_3.setText("当前状态");
		group_3.setBounds(10, 177, 501, 192);
		
		Composite composite = new Composite(group_3, SWT.BORDER);
		composite.setBounds(10, 20, 481, 24);
		
		Label label_1 = new Label(composite, SWT.NONE);
		label_1.setText("闸机");
		label_1.setAlignment(SWT.RIGHT);
		label_1.setBounds(17, 6, 39, 12);
		
		rb_up = new Button(composite, SWT.RADIO);
		rb_up.setText("起");
		rb_up.setBounds(63, 4, 39, 16);
		
		rb_down = new Button(composite, SWT.RADIO);
		rb_down.setText("落");
		rb_down.setBounds(113, 4, 39, 16);
		
		rb_stop = new Button(composite, SWT.RADIO);
		rb_stop.setText("停");
		rb_stop.setBounds(158, 4, 39, 16);
		
		rb_false = new Button(composite, SWT.RADIO);
		rb_false.setBounds(197, 4, 45, 16);
		rb_false.setText("无效");
		
		Composite composite_1 = new Composite(group_3, SWT.BORDER);
		composite_1.setBounds(10, 50, 242, 24);
		
		Label label_2 = new Label(composite_1, SWT.NONE);
		label_2.setText("内置语音");
		label_2.setBounds(10, 6, 50, 12);
		
		button_insideVoiceOpen = new Button(composite_1, SWT.RADIO);
		button_insideVoiceOpen.setText("打开");
		button_insideVoiceOpen.setBounds(63, 4, 45, 16);
		
		button_insideVoiceClose = new Button(composite_1, SWT.RADIO);
		button_insideVoiceClose.setText("关闭");
		button_insideVoiceClose.setBounds(113, 4, 45, 16);
		
		Composite composite_2 = new Composite(group_3, SWT.BORDER);
		composite_2.setBounds(258, 50, 233, 24);
		
		Label label_3 = new Label(composite_2, SWT.NONE);
		label_3.setText("内置显示屏");
		label_3.setBounds(10, 6, 67, 12);
		
		button_insideScreenOpen = new Button(composite_2, SWT.RADIO);
		button_insideScreenOpen.setText("打开");
		button_insideScreenOpen.setBounds(83, 4, 45, 16);
		
		button_insideScreenClose = new Button(composite_2, SWT.RADIO);
		button_insideScreenClose.setText("关闭");
		button_insideScreenClose.setBounds(134, 4, 45, 16);
		
		Composite composite_3 = new Composite(group_3, SWT.BORDER);
		composite_3.setBounds(258, 83, 233, 24);
		
		Label label_4 = new Label(composite_3, SWT.NONE);
		label_4.setText("外置显示屏");
		label_4.setBounds(10, 6, 67, 12);
		
		button_outsideScreenOpen = new Button(composite_3, SWT.RADIO);
		button_outsideScreenOpen.setText("打开");
		button_outsideScreenOpen.setBounds(83, 4, 45, 16);
		
		button_outsideScreenClose = new Button(composite_3, SWT.RADIO);
		button_outsideScreenClose.setText("关闭");
		button_outsideScreenClose.setBounds(134, 4, 45, 16);
		
		Composite composite_4 = new Composite(group_3, SWT.BORDER);
		composite_4.setBounds(10, 83, 242, 24);
		
		Label label_5 = new Label(composite_4, SWT.NONE);
		label_5.setText("外置语音");
		label_5.setBounds(10, 6, 50, 12);
		
		button_outsideVoiceOpen = new Button(composite_4, SWT.RADIO);
		button_outsideVoiceOpen.setText("打开");
		button_outsideVoiceOpen.setBounds(63, 4, 45, 16);
		
		button_outsideVoiceClose = new Button(composite_4, SWT.RADIO);
		button_outsideVoiceClose.setText("关闭");
		button_outsideVoiceClose.setBounds(113, 4, 45, 16);
		
		Composite composite_5 = new Composite(group_3, SWT.BORDER);
		composite_5.setBounds(10, 113, 481, 30);
		
		Label label_6 = new Label(composite_5, SWT.NONE);
		label_6.setText("内置显示屏内容:");
		label_6.setBounds(10, 6, 93, 12);
		
		text_insideScreenText = new Text(composite_5, SWT.BORDER);
		text_insideScreenText.setBounds(109, 3, 271, 18);
		
		Composite composite_6 = new Composite(group_3, SWT.BORDER);
		composite_6.setBounds(10, 149, 481, 30);
		
		Label label_7 = new Label(composite_6, SWT.NONE);
		label_7.setText("外置显示屏内容:");
		label_7.setBounds(10, 6, 93, 12);
		
		text_outsideScreenText = new Text(composite_6, SWT.BORDER);
		text_outsideScreenText.setBounds(109, 3, 271, 18);
		
		Group group = new Group(shell, SWT.NONE);
		group.setBounds(10, 10, 501, 35);
		group.setText("操作");
		
		Button button_1 = new Button(group, SWT.NONE);
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				presenter.connectService(txt_ip.getText(),txt_port.getText());
			}
		});
		button_1.setBounds(242, 10, 72, 22);
		button_1.setText("连接");
		
		Button button = new Button(group, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				presenter.changeDecretKey();
			}
		});
		button.setText("交换密钥");
		button.setBounds(402, 10, 72, 22);
		
		Label lblNewLabel = new Label(group, SWT.NONE);
		lblNewLabel.setBounds(10, 15, 40, 12);
		lblNewLabel.setText("IP地址:");
		
		txt_ip = new Text(group, SWT.BORDER);
		txt_ip.setText("127.0.0.1");
		txt_ip.setBounds(56, 12, 70, 18);
		
		Label label_8 = new Label(group, SWT.NONE);
		label_8.setText("端口:");
		label_8.setBounds(132, 15, 30, 12);
		
		txt_port = new Text(group, SWT.BORDER);
		txt_port.setText("9123");
		txt_port.setBounds(168, 12, 70, 18);
		
		Button button_15 = new Button(group, SWT.NONE);
		button_15.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				presenter.disConnect();
			}
		});
		button_15.setText("断开");
		button_15.setBounds(324, 10, 72, 22);
		
		Group group_4 = new Group(shell, SWT.NONE);
		group_4.setText("设备信息");
		group_4.setBounds(10, 51, 501, 120);
		
		Button button_16 = new Button(group_4, SWT.NONE);
		button_16.setBounds(401, 95, 90, 22);
		button_16.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String deviceName = txt_deviceName.getText().trim();
				boolean deviceInOutType = rb_deviceInOutType_in.getSelection();
				boolean deviceDisplayAndVoiceInside = rb_deviceDisplayAndVoiceInside_true.getSelection();
				boolean deviceDisplayAndVoiceOutside = rb_deviceDisplayAndVoiceOutside_true.getSelection();
				boolean deviceDisplaySupportChinese  =rb_deviceDisplaySupportChinese_true.getSelection();
				
				presenter.sendDeviceInfo(deviceName,deviceInOutType,deviceDisplayAndVoiceInside,deviceDisplayAndVoiceOutside,deviceDisplaySupportChinese);
			}
		});
		button_16.setText("发送设备信息");
		
		Composite composite_7 = new Composite(group_4, SWT.BORDER);
		composite_7.setBounds(10, 20, 244, 28);
		
		Label lblNewLabel_1 = new Label(composite_7, SWT.NONE);
		lblNewLabel_1.setBounds(10, 10, 54, 12);
		lblNewLabel_1.setText("设备名称:");
		
		txt_deviceName = new Text(composite_7, SWT.BORDER);
		txt_deviceName.setBounds(80, 4, 115, 18);
		txt_deviceName.setText("测试设备1");
		
		Composite composite_8 = new Composite(group_4, SWT.BORDER);
		composite_8.setBounds(10, 55, 244, 28);
		
		Label label_9 = new Label(composite_8, SWT.NONE);
		label_9.setText("设备类型:");
		label_9.setBounds(10, 10, 54, 12);
		
		rb_deviceInOutType_in = new Button(composite_8, SWT.RADIO);
		rb_deviceInOutType_in.setBounds(81, 6, 45, 16);
		rb_deviceInOutType_in.setText("进口");
		rb_deviceInOutType_in.setSelection(true);
		
		rb_deviceInOutType_out = new Button(composite_8, SWT.RADIO);
		rb_deviceInOutType_out.setText("出口");
		rb_deviceInOutType_out.setBounds(132, 8, 45, 16);
		
		Composite composite_9 = new Composite(group_4, SWT.BORDER);
		composite_9.setBounds(260, 20, 231, 28);
		
		Label label_10 = new Label(composite_9, SWT.NONE);
		label_10.setText("内置语音显示:");
		label_10.setBounds(10, 10, 81, 12);
		
		rb_deviceDisplayAndVoiceInside_true = new Button(composite_9, SWT.RADIO);
		rb_deviceDisplayAndVoiceInside_true.setText("支持");
		rb_deviceDisplayAndVoiceInside_true.setBounds(97, 4, 45, 16);
		rb_deviceDisplayAndVoiceInside_true.setSelection(true);
		
		rb_deviceDisplayAndVoiceInside_false = new Button(composite_9, SWT.RADIO);
		rb_deviceDisplayAndVoiceInside_false.setText("不支持");
		rb_deviceDisplayAndVoiceInside_false.setBounds(148, 6, 69, 16);
		
		Composite composite_10 = new Composite(group_4, SWT.BORDER);
		composite_10.setBounds(260, 55, 231, 28);
		
		Label label_11 = new Label(composite_10, SWT.NONE);
		label_11.setText("外置语音显示:");
		label_11.setBounds(10, 10, 81, 12);
		
		rb_deviceDisplayAndVoiceOutside_true = new Button(composite_10, SWT.RADIO);
		rb_deviceDisplayAndVoiceOutside_true.setText("支持");
		rb_deviceDisplayAndVoiceOutside_true.setBounds(97, 4, 45, 16);
		rb_deviceDisplayAndVoiceOutside_true.setSelection(true);
		
		rb_deviceDisplayAndVoiceOutside_false = new Button(composite_10, SWT.RADIO);
		rb_deviceDisplayAndVoiceOutside_false.setText("不支持");
		rb_deviceDisplayAndVoiceOutside_false.setBounds(148, 6, 69, 16);
		
		Composite composite_11 = new Composite(group_4, SWT.BORDER);
		composite_11.setBounds(10, 89, 244, 28);
		
		Label label_12 = new Label(composite_11, SWT.NONE);
		label_12.setText("中文指令:");
		label_12.setBounds(10, 10, 54, 12);
		
		rb_deviceDisplaySupportChinese_true = new Button(composite_11, SWT.RADIO);
		rb_deviceDisplaySupportChinese_true.setText("支持");
		rb_deviceDisplaySupportChinese_true.setBounds(81, 6, 45, 16);
		rb_deviceDisplaySupportChinese_true.setSelection(true);
		
		rb_deviceDisplaySupportChinese_false = new Button(composite_11, SWT.RADIO);
		rb_deviceDisplaySupportChinese_false.setText("不支持");
		rb_deviceDisplaySupportChinese_false.setBounds(132, 8, 68, 16);
		
		Group group_5 = new Group(shell, SWT.NONE);
		group_5.setText("刷卡");
		group_5.setBounds(10, 375, 501, 45);
		
		Label label = new Label(group_5, SWT.NONE);
		label.setBounds(10, 18, 40, 12);
		label.setAlignment(SWT.RIGHT);
		label.setText("卡号:");
		
		txt_cardNO = new Text(group_5, SWT.BORDER);
		txt_cardNO.setBounds(56, 15, 70, 18);
		txt_cardNO.setText("NO12345678");
		
		Button button_2 = new Button(group_5, SWT.NONE);
		button_2.setBounds(132, 13, 72, 22);
		button_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(txt_cardNO.getText().trim().equals("")){
					return;
				}
				presenter.sendCardNO(txt_cardNO.getText().trim(),txt_deviceName.getText().trim());
			}
		});
		button_2.setText("发送卡号");
	}
	
	public void println(final String text){
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				if(text_1.getText().length() > 5000){
					text_1.setText("");
				}
				text_1.append("\n"+text+"\n");
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
	
	public void state2Xml(Document document){
		Element rootElement = document.getRootElement();
		Element controlElement = rootElement.element("control");
		
		String gate = controlElement.element("gate").getText().trim();
		rb_up.setSelection(false);
		rb_down.setSelection(false);
		rb_stop.setSelection(false);
		rb_false.setSelection(false);
		if(gate.equals("up")){
			rb_up.setSelection(true);
		}
		if(gate.equals("down")){
			rb_down.setSelection(true);
		}
		if(gate.equals("stop")){
			rb_stop.setSelection(true);
		}
		if(gate.equals("false")){
			rb_false.setSelection(true);
		}
		
		String insideVoiceOpen = controlElement.element("insideVoice").getText();
		if(insideVoiceOpen.equals("true")){
			button_insideVoiceOpen.setSelection(true);
			button_insideVoiceClose.setSelection(false);
		}else{
			button_insideVoiceOpen.setSelection(false);
			button_insideVoiceClose.setSelection(true);
		}
		String outsideVoiceOpen = controlElement.element("outsideVoice").getText();
		if(outsideVoiceOpen.equals("true")){
			button_outsideVoiceOpen.setSelection(true);
			button_outsideVoiceClose.setSelection(false);
		}else{
			button_outsideVoiceOpen.setSelection(false);
			button_outsideVoiceClose.setSelection(true);
		}
		String insideScreen = controlElement.element("insideScreen").getText();
		if(insideScreen.equals("true")){
			button_insideScreenOpen.setSelection(true);
			button_insideScreenClose.setSelection(false);
		}else{
			button_insideScreenOpen.setSelection(false);
			button_insideScreenClose.setSelection(true);
		}
		String outsideScreen = controlElement.element("outsideScreen").getText();
		if(outsideScreen.equals("true")){
			button_outsideScreenOpen.setSelection(true);
			button_outsideScreenClose.setSelection(false);
		}else{
			button_outsideScreenOpen.setSelection(false);
			button_outsideScreenClose.setSelection(true);
		}
		String insideScreenAndVoiceData = controlElement.element("insideScreenAndVoiceData").getText();
		text_insideScreenText.setText(insideScreenAndVoiceData);
		String outsideScreenAndVoiceData = controlElement.element("outsideScreenAndVoiceData").getText();
		text_outsideScreenText.setText(outsideScreenAndVoiceData);
	}
}
