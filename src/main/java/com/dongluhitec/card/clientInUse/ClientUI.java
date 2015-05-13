package com.dongluhitec.card.clientInUse;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.DebugGraphics;

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
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.widgets.DateTime;
import static com.dongluhitec.card.clientInUse.Constants.DEV_MODE;

public class ClientUI {
    private ClientPresenter presenter;
    private ClientPresenterClient presenterClient;
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
    private Text text_1;
    private Text text_4;
    static final String preStr = "DLGX";

    // final String preStr="DLGX";

    /**
     * Launch the application.
     * 
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
        presenterClient = new ClientPresenterClient(this);
        if (DEV_MODE) {
            ClientPresenterClient.CardSN(text_4.getText().trim());
        } else {
            ClientPresenterClient.CardSN("");
        }

        while (!shlv.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        presenterClient.close();
        presenter.close();
    }

    /**
     * Create contents of the window.
     */
    protected void createContents() {
        shlv = new Shell();
        shlv.setSize(437, 673);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        shlv.setText(preStr + "停车场底层客户端  v" + sdf.format(new Date()));
        shlv.setLayout(new FormLayout());

        Group group = new Group(shlv, SWT.NONE);
        FormData fd_group = new FormData();
        fd_group.top = new FormAttachment(0);
        fd_group.right = new FormAttachment(0, 412);
        fd_group.left = new FormAttachment(0, 10);
        group.setLayoutData(fd_group);
        group.setText("操作");
        group.setLayout(new FormLayout());

        Composite composite = new Composite(group, SWT.BORDER);
        FormData fd_composite = new FormData();
        fd_composite.right = new FormAttachment(0, 196);
        fd_composite.top = new FormAttachment(0, 41);
        fd_composite.left = new FormAttachment(0, 7);
        composite.setLayoutData(fd_composite);
        RowLayout rl_composite = new RowLayout(SWT.HORIZONTAL);
        rl_composite.center = true;
        composite.setLayout(rl_composite);

        Label lblNewLabel = new Label(composite, SWT.NONE);
        lblNewLabel.setLayoutData(new RowData(60, SWT.DEFAULT));
        lblNewLabel.setText("内置语音");

        button_insideVoiceOpen = new Button(composite, SWT.RADIO);
        button_insideVoiceOpen.setLayoutData(new RowData(50, SWT.DEFAULT));
        button_insideVoiceOpen.setText("打开");
        button_insideVoiceOpen.setSelection(true);

        button_insideVoiceClose = new Button(composite, SWT.RADIO);
        button_insideVoiceClose.setLayoutData(new RowData(50, SWT.DEFAULT));
        button_insideVoiceClose.setText("关闭");

        Composite composite_1 = new Composite(group, SWT.BORDER);
        FormData fd_composite_1 = new FormData();
        fd_composite_1.right = new FormAttachment(0, 196);
        fd_composite_1.top = new FormAttachment(0, 74);
        fd_composite_1.left = new FormAttachment(0, 7);
        composite_1.setLayoutData(fd_composite_1);
        RowLayout rl_composite_1 = new RowLayout(SWT.HORIZONTAL);
        rl_composite_1.center = true;
        composite_1.setLayout(rl_composite_1);

        Label label = new Label(composite_1, SWT.NONE);
        label.setLayoutData(new RowData(60, SWT.DEFAULT));
        label.setText("外置语音");

        button_outsideVoiceOpen = new Button(composite_1, SWT.RADIO);
        button_outsideVoiceOpen.setLayoutData(new RowData(50, SWT.DEFAULT));
        button_outsideVoiceOpen.setText("打开");
        button_outsideVoiceOpen.setSelection(true);

        button_outsideVoiceClose = new Button(composite_1, SWT.RADIO);
        button_outsideVoiceClose.setLayoutData(new RowData(50, SWT.DEFAULT));
        button_outsideVoiceClose.setText("关闭");

        Composite composite_2 = new Composite(group, SWT.BORDER);
        FormData fd_composite_2 = new FormData();
        fd_composite_2.right = new FormAttachment(0, 397);
        fd_composite_2.top = new FormAttachment(0, 41);
        fd_composite_2.left = new FormAttachment(0, 208);
        composite_2.setLayoutData(fd_composite_2);
        RowLayout rl_composite_2 = new RowLayout(SWT.HORIZONTAL);
        rl_composite_2.center = true;
        composite_2.setLayout(rl_composite_2);

        Label label_1 = new Label(composite_2, SWT.NONE);
        label_1.setLayoutData(new RowData(60, SWT.DEFAULT));
        label_1.setText("内置屏显");

        button_insideScreenOpen = new Button(composite_2, SWT.RADIO);
        button_insideScreenOpen.setLayoutData(new RowData(50, SWT.DEFAULT));
        button_insideScreenOpen.setText("打开");
        button_insideScreenOpen.setSelection(true);

        button_insideScreenClose = new Button(composite_2, SWT.RADIO);
        button_insideScreenClose.setText("关闭");

        Composite composite_3 = new Composite(group, SWT.BORDER);
        FormData fd_composite_3 = new FormData();
        fd_composite_3.right = new FormAttachment(0, 397);
        fd_composite_3.top = new FormAttachment(0, 74);
        fd_composite_3.left = new FormAttachment(0, 208);
        composite_3.setLayoutData(fd_composite_3);
        RowLayout rl_composite_3 = new RowLayout(SWT.HORIZONTAL);
        rl_composite_3.center = true;
        composite_3.setLayout(rl_composite_3);

        Label label_2 = new Label(composite_3, SWT.NONE);
        label_2.setLayoutData(new RowData(60, SWT.DEFAULT));
        label_2.setText("外置屏显");

        button_outsideScreenOpen = new Button(composite_3, SWT.RADIO);
        button_outsideScreenOpen.setLayoutData(new RowData(50, SWT.DEFAULT));
        button_outsideScreenOpen.setText("打开");
        button_outsideScreenOpen.setSelection(true);

        button_outsideScreenClose = new Button(composite_3, SWT.RADIO);
        button_outsideScreenClose.setText("关闭");

        Composite composite_4 = new Composite(group, SWT.BORDER);
        FormData fd_composite_4 = new FormData();
        fd_composite_4.bottom = new FormAttachment(0, 35);
        fd_composite_4.right = new FormAttachment(0, 397);
        fd_composite_4.top = new FormAttachment(0, 11);
        fd_composite_4.left = new FormAttachment(0, 7);
        composite_4.setLayoutData(fd_composite_4);
        RowLayout rl_composite_4 = new RowLayout(SWT.HORIZONTAL);
        rl_composite_4.center = true;
        composite_4.setLayout(rl_composite_4);

        Label label_3 = new Label(composite_4, SWT.NONE);
        label_3.setLayoutData(new RowData(60, SWT.DEFAULT));
        label_3.setText("\u95F8\u673A");

        rb_up = new Button(composite_4, SWT.RADIO);
        rb_up.setLayoutData(new RowData(50, SWT.DEFAULT));
        rb_up.setText("起");
        rb_up.setSelection(true);

        rb_down = new Button(composite_4, SWT.RADIO);
        rb_down.setLayoutData(new RowData(50, SWT.DEFAULT));
        rb_down.setText("落");

        rb_stop = new Button(composite_4, SWT.RADIO);
        rb_stop.setLayoutData(new RowData(50, SWT.DEFAULT));
        rb_stop.setText("停");

        rb_false = new Button(composite_4, SWT.RADIO);
        rb_false.setLayoutData(new RowData(50, SWT.DEFAULT));
        rb_false.setText("无效");

        Composite composite_5 = new Composite(group, SWT.BORDER);
        FormData fd_composite_5 = new FormData();
        fd_composite_5.bottom = new FormAttachment(0, 134);
        fd_composite_5.right = new FormAttachment(0, 397);
        fd_composite_5.top = new FormAttachment(0, 104);
        fd_composite_5.left = new FormAttachment(0, 7);
        composite_5.setLayoutData(fd_composite_5);
        RowLayout rl_composite_5 = new RowLayout(SWT.HORIZONTAL);
        rl_composite_5.center = true;
        composite_5.setLayout(rl_composite_5);

        Label label_4 = new Label(composite_5, SWT.NONE);
        label_4.setLayoutData(new RowData(100, SWT.DEFAULT));
        label_4.setText("外置显示屏内容:");

        text_outsideScreenText = new Text(composite_5, SWT.BORDER);
        text_outsideScreenText.setLayoutData(new RowData(250, SWT.DEFAULT));
        text_outsideScreenText.setText("这是外置显示屏的内容");

        Composite composite_6 = new Composite(group, SWT.BORDER);
        FormData fd_composite_6 = new FormData();
        fd_composite_6.bottom = new FormAttachment(0, 170);
        fd_composite_6.right = new FormAttachment(0, 397);
        fd_composite_6.top = new FormAttachment(0, 140);
        fd_composite_6.left = new FormAttachment(0, 7);
        composite_6.setLayoutData(fd_composite_6);
        RowLayout rl_composite_6 = new RowLayout(SWT.HORIZONTAL);
        rl_composite_6.center = true;
        composite_6.setLayout(rl_composite_6);

        Label label_5 = new Label(composite_6, SWT.NONE);
        label_5.setLayoutData(new RowData(100, SWT.DEFAULT));
        label_5.setText("内置显示屏内容:");

        text_insideScreenText = new Text(composite_6, SWT.BORDER);
        text_insideScreenText.setLayoutData(new RowData(250, SWT.DEFAULT));
        text_insideScreenText.setText("这是内置显示屏的内容");

        Button btnNewButton = new Button(group, SWT.NONE);
        FormData fd_btnNewButton = new FormData();
        fd_btnNewButton.bottom = new FormAttachment(0, 199);
        fd_btnNewButton.right = new FormAttachment(0, 397);
        fd_btnNewButton.top = new FormAttachment(0, 177);
        fd_btnNewButton.left = new FormAttachment(0, 325);
        btnNewButton.setLayoutData(fd_btnNewButton);
        btnNewButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                presenter.sendDeviceControl();
            }
        });
        btnNewButton.setText("\u53D1\u9001");

        Button button_11 = new Button(group, SWT.NONE);
        FormData fd_button_11 = new FormData();
        fd_button_11.bottom = new FormAttachment(0, 199);
        fd_button_11.right = new FormAttachment(0, 319);
        fd_button_11.top = new FormAttachment(0, 177);
        fd_button_11.left = new FormAttachment(0, 247);
        button_11.setLayoutData(fd_button_11);
        button_11.setText("\u91CD\u7F6E");

        Group group_1 = new Group(shlv, SWT.NONE);
        FormData fd_group_1 = new FormData();
        fd_group_1.right = new FormAttachment(group, 0, SWT.RIGHT);
        group_1.setLayoutData(fd_group_1);

        group_1.setText("密文");

        text_2 = new Text(group_1, SWT.BORDER | SWT.WRAP | SWT.H_SCROLL
                | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
        text_2.setBounds(10, 20, 194, 238);

        Group group_2 = new Group(shlv, SWT.NONE);
        FormData fd_group_2 = new FormData();
        fd_group_2.top = new FormAttachment(group_1, 0, SWT.TOP);
        fd_group_2.right = new FormAttachment(group_1, -2);
        group_2.setLayoutData(fd_group_2);
        group_2.setText("明文");

        text_3 = new Text(group_2, SWT.BORDER | SWT.WRAP | SWT.H_SCROLL
                | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
        text_3.setBounds(10, 24, 180, 234);
        fd_group.bottom = new FormAttachment(100, -353);

        Button button_12 = new Button(shlv, SWT.NONE);
        FormData fd_button_12 = new FormData();
        fd_button_12.bottom = new FormAttachment(100, -10);
        fd_button_12.left = new FormAttachment(0, 91);
        fd_button_12.right = new FormAttachment(100, -92);
        button_12.setLayoutData(fd_button_12);
        button_12.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                text_2.setText("");
                text_3.setText("");
            }
        });
        button_12.setText("\u6E05\u7A7A");

        Composite composite_8 = new Composite(shlv, SWT.BORDER);
        fd_group_1.top = new FormAttachment(composite_8, 6);
        RowLayout rl_composite_8 = new RowLayout(SWT.HORIZONTAL);
        rl_composite_8.center = true;
        composite_8.setLayout(rl_composite_8);
        FormData fd_composite_8 = new FormData();
        fd_composite_8.top = new FormAttachment(group, 6);
        fd_composite_8.left = new FormAttachment(group, 10, SWT.LEFT);

        Group group_3 = new Group(group, SWT.NONE);
        FormData fd_group_3 = new FormData();
        fd_group_3.right = new FormAttachment(composite_2, 0, SWT.RIGHT);
        fd_group_3.top = new FormAttachment(btnNewButton, 25);
        fd_group_3.left = new FormAttachment(0, 4);
        group_3.setLayoutData(fd_group_3);

        Button btnConnectToSelfButton = new Button(group_3, SWT.NONE);
        btnConnectToSelfButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent arg0) {
                presenterClient.connectService("127.0.0.1", "9123");
            }
        });
        btnConnectToSelfButton.setBounds(0, 10, 76, 27);
        btnConnectToSelfButton.setText("open");

        Button btnSimulateSend = new Button(group_3, SWT.NONE);
        btnSimulateSend.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent arg0) {
                presenterClient.sendDeviceControl();
            }
        });
        btnSimulateSend.setBounds(77, 10, 76, 27);
        btnSimulateSend.setText("Send");

        text_4 = new Text(group_3, SWT.BORDER);
        text_4.setText("01234567");
        text_4.setBounds(257, 10, 126, 27);

        Button button = new Button(group_3, SWT.NONE);
        button.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent arg0) {
                ClientPresenterClient.CardSN(text_4.getText().trim());
            }
        });

        button.setText("设置卡序列号");
        button.setBounds(159, 10, 92, 27);
        composite_8.setLayoutData(fd_composite_8);

        Label label_7 = new Label(composite_8, SWT.NONE);
        label_7.setLayoutData(new RowData(100, -1));
        label_7.setText("设   置   广   告:");

        text_1 = new Text(composite_8, SWT.BORDER);
        text_1.setLayoutData(new RowData(200, SWT.DEFAULT));

        Button button_1 = new Button(composite_8, SWT.NONE);
        button_1.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                presenter.sendAD();
            }
        });
        button_1.setLayoutData(new RowData(63, -1));
        button_1.setText("设置");
    }

    public void println(final String text) {
        Display.getDefault().asyncExec(new Runnable() {
            public void run() {
                if (text_3.getText().length() > 5000) {
                    text_3.setText("");
                }
                text_3.append("\n" + text + "\n");
            }
        });
    }

    public void println_encode(final String text) {
        Display.getDefault().asyncExec(new Runnable() {
            public void run() {
                if (text_2.getText().length() > 5000) {
                    text_2.setText("");
                }
                text_2.append("\n" + text + "\n");
            }
        });
    }

    public Document state2Xml() {
        String gate = "";
        if (rb_up.getSelection() == true) {
            gate = "up";
        }
        if (rb_down.getSelection() == true) {
            gate = "down";
        }
        if (rb_stop.getSelection() == true) {
            gate = "stop";
        }
        if (rb_false.getSelection() == true) {
            gate = "false";
        }

        String insideVoiceOpen = button_insideVoiceOpen.getSelection() == true ? "true"
                : "false";
        String outsideVoiceOpen = button_outsideVoiceOpen.getSelection() == true ? "true"
                : "false";
        String insideScreenOpen = button_insideScreenOpen.getSelection() == true ? "true"
                : "false";
        String outsideScreenOpen = button_outsideScreenOpen.getSelection() == true ? "true"
                : "false";
        String insideScreenText = text_insideScreenText.getText().trim();
        String outsideScreenText = text_outsideScreenText.getText().trim();

        return genXML(gate, insideVoiceOpen, outsideVoiceOpen,
                insideScreenOpen, outsideScreenOpen, insideScreenText,
                outsideScreenText);
    }

    public Document Ok2Xml() {
        String gate = "";

        String insideVoiceOpen = "";
        String outsideVoiceOpen = "";
        String insideScreenOpen = "";
        String outsideScreenOpen = "";
        String insideScreenText = "";
        String outsideScreenText = "";

        return genXML(gate, insideVoiceOpen, outsideVoiceOpen,
                insideScreenOpen, outsideScreenOpen, insideScreenText,
                outsideScreenText);
    }
    
    public Document genXML(String gate, String insideVoiceOpen,
            String outsideVoiceOpen, String insideScreenOpen,
            String outsideScreenOpen, String insideScreenText,
            String outsideScreenText) {
        Document document = DocumentHelper.createDocument();
        Element rootElement = document.addElement("dongluCarpark");
        rootElement.addAttribute("type", "deviceControl");
        Element controlElement = rootElement.addElement("control");

        controlElement.addElement("gate").setText(gate);
        controlElement.addElement("insideVoice").setText(insideVoiceOpen);

        controlElement.addElement("outsideVoice").setText(outsideVoiceOpen);

        controlElement.addElement("insideScreen").setText(insideScreenOpen);

        controlElement.addElement("outsideScreen").setText(outsideScreenOpen);

        controlElement.addElement("insideScreenAndVoiceData").setText(
                insideScreenText);

        controlElement.addElement("outsideScreenAndVoiceData").setText(
                outsideScreenText);

        return document;
    }

    public Document ad2Xml() {
        Document document = DocumentHelper.createDocument();
        Element rootElement = document.addElement("dongluCarpark");
        rootElement.addAttribute("type", "ad");
        Element dateElement = rootElement.addElement("ad");
        String trim = text_1.getText().trim();
        dateElement.setText(trim);
        return document;
    }
}
