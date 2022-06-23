

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;

public class SetBorder方法测试2205262241 {
	
	
	static Frame frame = new Frame("SetBorder方法测试2205262241");
	
	static JLabel jlb;
	
	static {
		frame.addWindowListener(new WindowAdapter() {@Override public void windowClosing(WindowEvent event) {frame.dispose();System.exit(0);}});
		frame.setBounds(100,50,1800,900);
		frame.setLayout(new GridLayout(0, 3, 5, 5));
		
		jlb = new JLabel("BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.red, Color.blue)"); frame.add(jlb);
		jlb.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.red, Color.blue));
		
		jlb = new JLabel("BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.red, Color.blue)"); frame.add(jlb);
		jlb.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.red, Color.blue));
		
		jlb = new JLabel("""
				<html>
					<div>BorderFactory.createEmptyBorder(60, 200, 0, 0)</div><hr/>
					<div>EmptyBorder不可见,但缩小了文字区域</div>
					<div>可以用于移动文字</div>
				</html>
				"""); frame.add(jlb);
		jlb.setBorder(BorderFactory.createEmptyBorder(60, 200, 0, 0)); jlb.setBackground(Color.yellow); jlb.setOpaque(true);
		
		jlb = new JLabel("BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, Color.YELLOW, Color.GREEN)"); frame.add(jlb);
		jlb.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, Color.YELLOW, Color.GREEN));
		
		jlb = new JLabel("BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.YELLOW, Color.GREEN)"); frame.add(jlb);
		jlb.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.YELLOW, Color.GREEN));
		
		jlb = new JLabel("BorderFactory.createLineBorder(Color.BLACK, 30, true)"); frame.add(jlb);
		jlb.setBorder(BorderFactory.createLineBorder(Color.BLACK, 30, true)); //true是圆角
		
		jlb = new JLabel("BorderFactory.createLineBorder(Color.BLACK, 30, false)"); frame.add(jlb);
		jlb.setBorder(BorderFactory.createLineBorder(Color.BLACK, 30, false));
		
		jlb = new JLabel("BorderFactory.createLoweredBevelBorder()"); frame.add(jlb);
		jlb.setBorder(BorderFactory.createLoweredBevelBorder());
		
		jlb = new JLabel("BorderFactory.createLoweredSoftBevelBorder()"); frame.add(jlb);
		jlb.setBorder(BorderFactory.createLoweredSoftBevelBorder());
		
		jlb = new JLabel("BorderFactory.createRaisedBevelBorder()"); frame.add(jlb);
		jlb.setBorder(BorderFactory.createRaisedBevelBorder());
		
		jlb = new JLabel("BorderFactory.createRaisedSoftBevelBorder()"); frame.add(jlb);
		jlb.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		
		jlb = new JLabel("BorderFactory.createSoftBevelBorder(SoftBevelBorder.LOWERED, Color.ORANGE, Color.CYAN)"); frame.add(jlb);
		jlb.setBorder(BorderFactory.createSoftBevelBorder(SoftBevelBorder.LOWERED, Color.ORANGE, Color.CYAN));
		
		jlb = new JLabel("BorderFactory.createSoftBevelBorder(SoftBevelBorder.RAISED, Color.ORANGE, Color.CYAN)"); frame.add(jlb);
		jlb.setBorder(BorderFactory.createSoftBevelBorder(SoftBevelBorder.RAISED, Color.ORANGE, Color.CYAN));
		
		jlb = new JLabel("BorderFactory.createCompoundBorder()"); frame.add(jlb);
		jlb.setBorder(BorderFactory.createCompoundBorder());
		
		jlb = new JLabel("BorderFactory.createMatteBorder(10, 20, 30, 40, Color.BLACK)"); frame.add(jlb);
		jlb.setBorder(BorderFactory.createMatteBorder(10, 20, 30, 40, Color.BLACK));
		
		jlb = new JLabel("BorderFactory.createTitledBorder(\"如同Html的FieldSet\")"); frame.add(jlb);
		jlb.setBorder(BorderFactory.createTitledBorder("如同Html的FieldSet"));
		
		jlb = new JLabel("BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(10, 20, 30, 40, Color.BLACK) , \"如同Html的FieldSet\")"); frame.add(jlb);
		jlb.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(10, 20, 30, 40, Color.BLACK) , "如同Html的FieldSet"));
		
		jlb = new JLabel("""
				<html>
					<pre>
					BorderFactory.createTitledBorder(
						BorderFactory.createMatteBorder(5, 10, 15, 20, Color.DARK_GRAY)
						, "如同Html的FieldSet", TitledBorder.CENTER, TitledBorder.BOTTOM
						, new Font("宋体",Font.BOLD,20), Color.RED
					)
					</pre>
				</html>
				"""); frame.add(jlb);
		jlb.setBorder(
				BorderFactory.createTitledBorder(
					BorderFactory.createMatteBorder(5, 10, 15, 20, Color.DARK_GRAY)
					, "如同Html的FieldSet"
					, TitledBorder.CENTER, TitledBorder.BOTTOM
					, new Font("宋体",Font.BOLD,20), Color.RED
				)
			);
		
		
	}
	
	public static void main(String...arguments) {
		frame.setVisible(true);
	}

}

