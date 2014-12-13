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
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.RowData;

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
	private Text text_ad;

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
		shell = new Shell(SWT.MIN|SWT.CLOSE);
		shell.setSize(529, 698);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		shell.setText("东陆高新停车场底层服务器v"+sdf.format(new Date()));
		shell.setLayout(new FormLayout());
		
		Group group_1 = new Group(shell, SWT.NONE);
		FormData fd_group_1 = new FormData();
		fd_group_1.right = new FormAttachment(0, 265);
		fd_group_1.top = new FormAttachment(0, 452);
		fd_group_1.left = new FormAttachment(0, 10);
		group_1.setLayoutData(fd_group_1);
		group_1.setText("密文");
		
		text_1 = new Text(group_1, SWT.BORDER | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		text_1.setBounds(10, 20, 235, 156);
		
		Group group_2 = new Group(shell, SWT.NONE);
		FormData fd_group_2 = new FormData();
		fd_group_2.right = new FormAttachment(0, 511);
		fd_group_2.top = new FormAttachment(0, 452);
		fd_group_2.left = new FormAttachment(0, 271);
		group_2.setLayoutData(fd_group_2);
		group_2.setText("译文");
		
		text_2 = new Text(group_2, SWT.BORDER | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		text_2.setBounds(10, 20, 220, 156);
		
		Button button_3 = new Button(shell, SWT.NONE);
		FormData fd_button_3 = new FormData();
		fd_button_3.bottom = new FormAttachment(0, 659);
		fd_button_3.right = new FormAttachment(0, 315);
		fd_button_3.top = new FormAttachment(0, 637);
		fd_button_3.left = new FormAttachment(0, 243);
		button_3.setLayoutData(fd_button_3);
		button_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				text_1.setText("");
				text_2.setText("");
			}
		});
		button_3.setText("清空");
		
		Group group_3 = new Group(shell, SWT.NONE);
		FormData fd_group_3 = new FormData();
		fd_group_3.right = new FormAttachment(0, 511);
		fd_group_3.top = new FormAttachment(0, 177);
		fd_group_3.left = new FormAttachment(0, 10);
		group_3.setLayoutData(fd_group_3);
		group_3.setText("当前状态");
		group_3.setLayout(new FormLayout());
		
		Composite composite = new Composite(group_3, SWT.BORDER);
		FormData fd_composite = new FormData();
		fd_composite.right = new FormAttachment(0, 488);
		fd_composite.top = new FormAttachment(0, 5);
		fd_composite.left = new FormAttachment(0, 7);
		composite.setLayoutData(fd_composite);
		RowLayout rl_composite = new RowLayout(SWT.HORIZONTAL);
		rl_composite.center = true;
		composite.setLayout(rl_composite);
		
		Label label_1 = new Label(composite, SWT.NONE);
		label_1.setLayoutData(new RowData(60, SWT.DEFAULT));
		label_1.setText("闸机");
		
		rb_up = new Button(composite, SWT.RADIO);
		rb_up.setLayoutData(new RowData(50, SWT.DEFAULT));
		rb_up.setText("起");
		
		rb_down = new Button(composite, SWT.RADIO);
		rb_down.setLayoutData(new RowData(50, SWT.DEFAULT));
		rb_down.setText("落");
		
		rb_stop = new Button(composite, SWT.RADIO);
		rb_stop.setLayoutData(new RowData(50, SWT.DEFAULT));
		rb_stop.setText("停");
		
		Composite composite_1 = new Composite(group_3, SWT.BORDER);
		FormData fd_composite_1 = new FormData();
		fd_composite_1.right = new FormAttachment(0, 249);
		fd_composite_1.top = new FormAttachment(0, 35);
		fd_composite_1.left = new FormAttachment(0, 7);
		composite_1.setLayoutData(fd_composite_1);
		RowLayout rl_composite_1 = new RowLayout(SWT.HORIZONTAL);
		rl_composite_1.center = true;
		composite_1.setLayout(rl_composite_1);
		
		Label label_2 = new Label(composite_1, SWT.NONE);
		label_2.setLayoutData(new RowData(60, SWT.DEFAULT));
		label_2.setText("内置语音");
		
		button_insideVoiceOpen = new Button(composite_1, SWT.RADIO);
		button_insideVoiceOpen.setLayoutData(new RowData(50, SWT.DEFAULT));
		button_insideVoiceOpen.setText("打开");
		
		button_insideVoiceClose = new Button(composite_1, SWT.RADIO);
		button_insideVoiceClose.setLayoutData(new RowData(50, SWT.DEFAULT));
		button_insideVoiceClose.setText("关闭");
		
		Composite composite_2 = new Composite(group_3, SWT.BORDER);
		FormData fd_composite_2 = new FormData();
		fd_composite_2.right = new FormAttachment(0, 488);
		fd_composite_2.top = new FormAttachment(0, 35);
		fd_composite_2.left = new FormAttachment(0, 255);
		composite_2.setLayoutData(fd_composite_2);
		RowLayout rl_composite_2 = new RowLayout(SWT.HORIZONTAL);
		rl_composite_2.center = true;
		composite_2.setLayout(rl_composite_2);
		
		Label label_3 = new Label(composite_2, SWT.NONE);
		label_3.setLayoutData(new RowData(60, SWT.DEFAULT));
		label_3.setText("内置显示屏");
		
		button_insideScreenOpen = new Button(composite_2, SWT.RADIO);
		button_insideScreenOpen.setLayoutData(new RowData(50, SWT.DEFAULT));
		button_insideScreenOpen.setText("打开");
		
		button_insideScreenClose = new Button(composite_2, SWT.RADIO);
		button_insideScreenClose.setLayoutData(new RowData(50, SWT.DEFAULT));
		button_insideScreenClose.setText("关闭");
		
		Composite composite_3 = new Composite(group_3, SWT.BORDER);
		FormData fd_composite_3 = new FormData();
		fd_composite_3.right = new FormAttachment(0, 488);
		fd_composite_3.top = new FormAttachment(0, 68);
		fd_composite_3.left = new FormAttachment(0, 255);
		composite_3.setLayoutData(fd_composite_3);
		RowLayout rl_composite_3 = new RowLayout(SWT.HORIZONTAL);
		rl_composite_3.center = true;
		composite_3.setLayout(rl_composite_3);
		
		Label label_4 = new Label(composite_3, SWT.NONE);
		label_4.setLayoutData(new RowData(60, SWT.DEFAULT));
		label_4.setText("外置显示屏");
		
		button_outsideScreenOpen = new Button(composite_3, SWT.RADIO);
		button_outsideScreenOpen.setLayoutData(new RowData(50, SWT.DEFAULT));
		button_outsideScreenOpen.setText("打开");
		
		button_outsideScreenClose = new Button(composite_3, SWT.RADIO);
		button_outsideScreenClose.setLayoutData(new RowData(50, SWT.DEFAULT));
		button_outsideScreenClose.setText("关闭");
		
		Composite composite_4 = new Composite(group_3, SWT.BORDER);
		FormData fd_composite_4 = new FormData();
		fd_composite_4.right = new FormAttachment(0, 249);
		fd_composite_4.top = new FormAttachment(0, 68);
		fd_composite_4.left = new FormAttachment(0, 7);
		composite_4.setLayoutData(fd_composite_4);
		RowLayout rl_composite_4 = new RowLayout(SWT.HORIZONTAL);
		rl_composite_4.center = true;
		composite_4.setLayout(rl_composite_4);
		
		Label label_5 = new Label(composite_4, SWT.NONE);
		label_5.setLayoutData(new RowData(60, SWT.DEFAULT));
		label_5.setText("外置语音");
		
		button_outsideVoiceOpen = new Button(composite_4, SWT.RADIO);
		button_outsideVoiceOpen.setLayoutData(new RowData(50, SWT.DEFAULT));
		button_outsideVoiceOpen.setText("打开");
		
		button_outsideVoiceClose = new Button(composite_4, SWT.RADIO);
		button_outsideVoiceClose.setLayoutData(new RowData(50, SWT.DEFAULT));
		button_outsideVoiceClose.setText("关闭");
		
		Composite composite_5 = new Composite(group_3, SWT.BORDER);
		FormData fd_composite_5 = new FormData();
		fd_composite_5.right = new FormAttachment(0, 488);
		fd_composite_5.bottom = new FormAttachment(0, 128);
		fd_composite_5.top = new FormAttachment(0, 98);
		fd_composite_5.left = new FormAttachment(0, 7);
		composite_5.setLayoutData(fd_composite_5);
		RowLayout rl_composite_5 = new RowLayout(SWT.HORIZONTAL);
		rl_composite_5.center = true;
		composite_5.setLayout(rl_composite_5);
		
		Label label_6 = new Label(composite_5, SWT.NONE);
		label_6.setText("内置显示屏内容:");
		
		text_insideScreenText = new Text(composite_5, SWT.BORDER);
		text_insideScreenText.setLayoutData(new RowData(250, SWT.DEFAULT));
		
		Composite composite_6 = new Composite(group_3, SWT.BORDER);
		FormData fd_composite_6 = new FormData();
		fd_composite_6.bottom = new FormAttachment(0, 164);
		fd_composite_6.right = new FormAttachment(0, 488);
		fd_composite_6.top = new FormAttachment(0, 134);
		fd_composite_6.left = new FormAttachment(0, 7);
		composite_6.setLayoutData(fd_composite_6);
		RowLayout rl_composite_6 = new RowLayout(SWT.HORIZONTAL);
		rl_composite_6.center = true;
		composite_6.setLayout(rl_composite_6);
		
		Label label_7 = new Label(composite_6, SWT.NONE);
		label_7.setText("外置显示屏内容:");
		
		text_outsideScreenText = new Text(composite_6, SWT.BORDER);
		text_outsideScreenText.setLayoutData(new RowData(250, SWT.DEFAULT));
		
		Composite composite_12 = new Composite(group_3, SWT.BORDER);
		FormData fd_composite_12 = new FormData();
		fd_composite_12.bottom = new FormAttachment(0, 200);
		fd_composite_12.right = new FormAttachment(0, 488);
		fd_composite_12.top = new FormAttachment(0, 170);
		fd_composite_12.left = new FormAttachment(0, 7);
		composite_12.setLayoutData(fd_composite_12);
		RowLayout rl_composite_12 = new RowLayout(SWT.HORIZONTAL);
		rl_composite_12.center = true;
		composite_12.setLayout(rl_composite_12);
		
		Label label_13 = new Label(composite_12, SWT.NONE);
		label_13.setText("显   示   广   告:");
		
		text_ad = new Text(composite_12, SWT.BORDER);
		text_ad.setLayoutData(new RowData(250, SWT.DEFAULT));
		
		Group group = new Group(shell, SWT.NONE);
		FormData fd_group = new FormData();
		fd_group.bottom = new FormAttachment(0, 53);
		fd_group.right = new FormAttachment(0, 511);
		fd_group.top = new FormAttachment(0, 10);
		fd_group.left = new FormAttachment(0, 10);
		group.setLayoutData(fd_group);
		group.setText("操作");
		RowLayout rl_group = new RowLayout(SWT.HORIZONTAL);
		rl_group.marginTop = 0;
		rl_group.center = true;
		group.setLayout(rl_group);
		
		Label lblNewLabel = new Label(group, SWT.NONE);
		lblNewLabel.setText("IP地址:");
		
		txt_ip = new Text(group, SWT.BORDER);
		txt_ip.setLayoutData(new RowData(100, SWT.DEFAULT));
		txt_ip.setText("127.0.0.1");
		
		Label label_8 = new Label(group, SWT.NONE);
		label_8.setText("端口:");
		
		txt_port = new Text(group, SWT.BORDER);
		txt_port.setLayoutData(new RowData(50, SWT.DEFAULT));
		txt_port.setText("9123");
		
		Button button_1 = new Button(group, SWT.NONE);
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				presenter.connectService(txt_ip.getText(),txt_port.getText());
			}
		});
		button_1.setText("连接");
		
		Button button = new Button(group, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				presenter.changeDecretKey();
			}
		});
		button.setText("交换密钥");
		
		Button button_15 = new Button(group, SWT.NONE);
		button_15.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				presenter.disConnect();
			}
		});
		button_15.setText("断开");
		
		Group group_4 = new Group(shell, SWT.NONE);
		FormData fd_group_4 = new FormData();
		fd_group_4.top = new FormAttachment(0, 54);
		fd_group_4.left = new FormAttachment(group_1, 0, SWT.LEFT);
		fd_group_4.right = new FormAttachment(0, 511);
		group_4.setLayoutData(fd_group_4);
		group_4.setText("设备信息");
		group_4.setLayout(new FormLayout());
		
		Button button_16 = new Button(group_4, SWT.NONE);
		FormData fd_button_16 = new FormData();
		fd_button_16.bottom = new FormAttachment(0, 102);
		fd_button_16.right = new FormAttachment(0, 488);
		fd_button_16.top = new FormAttachment(0, 80);
		fd_button_16.left = new FormAttachment(0, 398);
		button_16.setLayoutData(fd_button_16);
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
		FormData fd_composite_7 = new FormData();
		fd_composite_7.bottom = new FormAttachment(0, 33);
		fd_composite_7.right = new FormAttachment(0, 251);
		fd_composite_7.top = new FormAttachment(0, 5);
		fd_composite_7.left = new FormAttachment(0, 7);
		composite_7.setLayoutData(fd_composite_7);
		RowLayout rl_composite_7 = new RowLayout(SWT.HORIZONTAL);
		rl_composite_7.center = true;
		composite_7.setLayout(rl_composite_7);
		
		Label lblNewLabel_1 = new Label(composite_7, SWT.NONE);
		lblNewLabel_1.setLayoutData(new RowData(60, SWT.DEFAULT));
		lblNewLabel_1.setText("设备名称:");
		
		txt_deviceName = new Text(composite_7, SWT.BORDER);
		txt_deviceName.setLayoutData(new RowData(150, SWT.DEFAULT));
		txt_deviceName.setText("测试设备1");
		
		Composite composite_8 = new Composite(group_4, SWT.BORDER);
		FormData fd_composite_8 = new FormData();
		fd_composite_8.right = new FormAttachment(0, 251);
		fd_composite_8.top = new FormAttachment(0, 40);
		fd_composite_8.left = new FormAttachment(0, 7);
		composite_8.setLayoutData(fd_composite_8);
		RowLayout rl_composite_8 = new RowLayout(SWT.HORIZONTAL);
		rl_composite_8.center = true;
		composite_8.setLayout(rl_composite_8);
		
		Label label_9 = new Label(composite_8, SWT.NONE);
		label_9.setLayoutData(new RowData(60, SWT.DEFAULT));
		label_9.setText("设备类型:");
		
		rb_deviceInOutType_in = new Button(composite_8, SWT.RADIO);
		rb_deviceInOutType_in.setLayoutData(new RowData(50, SWT.DEFAULT));
		rb_deviceInOutType_in.setText("进口");
		rb_deviceInOutType_in.setSelection(true);
		
		rb_deviceInOutType_out = new Button(composite_8, SWT.RADIO);
		rb_deviceInOutType_out.setLayoutData(new RowData(50, SWT.DEFAULT));
		rb_deviceInOutType_out.setText("出口");
		
		Composite composite_9 = new Composite(group_4, SWT.BORDER);
		FormData fd_composite_9 = new FormData();
		fd_composite_9.bottom = new FormAttachment(0, 33);
		fd_composite_9.right = new FormAttachment(0, 488);
		fd_composite_9.top = new FormAttachment(0, 5);
		fd_composite_9.left = new FormAttachment(0, 257);
		composite_9.setLayoutData(fd_composite_9);
		RowLayout rl_composite_9 = new RowLayout(SWT.HORIZONTAL);
		rl_composite_9.center = true;
		composite_9.setLayout(rl_composite_9);
		
		Label label_10 = new Label(composite_9, SWT.NONE);
		label_10.setText("内置语音显示:");
		
		rb_deviceDisplayAndVoiceInside_true = new Button(composite_9, SWT.RADIO);
		rb_deviceDisplayAndVoiceInside_true.setText("支持");
		rb_deviceDisplayAndVoiceInside_true.setSelection(true);
		
		rb_deviceDisplayAndVoiceInside_false = new Button(composite_9, SWT.RADIO);
		rb_deviceDisplayAndVoiceInside_false.setText("不支持");
		
		Composite composite_10 = new Composite(group_4, SWT.BORDER);
		FormData fd_composite_10 = new FormData();
		fd_composite_10.bottom = new FormAttachment(0, 68);
		fd_composite_10.right = new FormAttachment(0, 488);
		fd_composite_10.top = new FormAttachment(0, 40);
		fd_composite_10.left = new FormAttachment(0, 257);
		composite_10.setLayoutData(fd_composite_10);
		RowLayout rl_composite_10 = new RowLayout(SWT.HORIZONTAL);
		rl_composite_10.center = true;
		composite_10.setLayout(rl_composite_10);
		
		Label label_11 = new Label(composite_10, SWT.NONE);
		label_11.setText("外置语音显示:");
		
		rb_deviceDisplayAndVoiceOutside_true = new Button(composite_10, SWT.RADIO);
		rb_deviceDisplayAndVoiceOutside_true.setText("支持");
		rb_deviceDisplayAndVoiceOutside_true.setSelection(true);
		
		rb_deviceDisplayAndVoiceOutside_false = new Button(composite_10, SWT.RADIO);
		rb_deviceDisplayAndVoiceOutside_false.setText("不支持");
		
		Composite composite_11 = new Composite(group_4, SWT.BORDER);
		FormData fd_composite_11 = new FormData();
		fd_composite_11.right = new FormAttachment(0, 251);
		fd_composite_11.top = new FormAttachment(0, 74);
		fd_composite_11.left = new FormAttachment(0, 7);
		composite_11.setLayoutData(fd_composite_11);
		RowLayout rl_composite_11 = new RowLayout(SWT.HORIZONTAL);
		rl_composite_11.center = true;
		composite_11.setLayout(rl_composite_11);
		
		Label label_12 = new Label(composite_11, SWT.NONE);
		label_12.setLayoutData(new RowData(60, SWT.DEFAULT));
		label_12.setText("中文指令:");
		
		rb_deviceDisplaySupportChinese_true = new Button(composite_11, SWT.RADIO);
		rb_deviceDisplaySupportChinese_true.setLayoutData(new RowData(50, SWT.DEFAULT));
		rb_deviceDisplaySupportChinese_true.setText("支持");
		rb_deviceDisplaySupportChinese_true.setSelection(true);
		
		rb_deviceDisplaySupportChinese_false = new Button(composite_11, SWT.RADIO);
		rb_deviceDisplaySupportChinese_false.setLayoutData(new RowData(60, SWT.DEFAULT));
		rb_deviceDisplaySupportChinese_false.setText("不支持");
		
		Group group_5 = new Group(shell, SWT.NONE);
		FormData fd_group_5 = new FormData();
		fd_group_5.bottom = new FormAttachment(0, 446);
		fd_group_5.right = new FormAttachment(0, 511);
		fd_group_5.top = new FormAttachment(0, 401);
		fd_group_5.left = new FormAttachment(0, 10);
		group_5.setLayoutData(fd_group_5);
		group_5.setText("刷卡");
		RowLayout rl_group_5 = new RowLayout(SWT.HORIZONTAL);
		rl_group_5.center = true;
		group_5.setLayout(rl_group_5);
		
		Label label = new Label(group_5, SWT.NONE);
		label.setAlignment(SWT.RIGHT);
		label.setText("卡号:");
		
		txt_cardNO = new Text(group_5, SWT.BORDER);
		txt_cardNO.setLayoutData(new RowData(150, SWT.DEFAULT));
		txt_cardNO.setText("NO12345678");
		
		Button button_2 = new Button(group_5, SWT.NONE);
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
		if(gate.equals("up")){
			rb_up.setSelection(true);
			rb_down.setSelection(false);
			rb_stop.setSelection(false);
		}
		if(gate.equals("down")){
			rb_up.setSelection(false);
			rb_down.setSelection(true);
			rb_stop.setSelection(false);
		}
		if(gate.equals("stop")){
			rb_up.setSelection(false);
			rb_down.setSelection(false);
			rb_stop.setSelection(true);
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

	public void ad2Xml(Document document) {
		Element rootElement = document.getRootElement();
		Element adElement = rootElement.element("ad");
		text_ad.setText(adElement.getTextTrim());
	}
}