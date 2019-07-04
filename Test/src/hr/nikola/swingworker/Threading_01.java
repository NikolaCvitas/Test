package hr.nikola.swingworker;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

/** @see http://stackoverflow.com/questions/4637215 */
public class Threading_01 extends JFrame {

	private static final String s = "0.00";
	private JProgressBar progressBar = new JProgressBar(0, 100);
	private JLabel label = new JLabel(s, JLabel.CENTER);

	public Threading_01() {
		this.setLayout(new GridLayout(0, 1));
		this.setTitle("√2");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(progressBar);
		this.add(label);
		this.setSize(161, 100);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public void runCalc() {
		//    progressBar.setIndeterminate(true);
		TwoWorker task = new TwoWorker();
		task.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent e) {
				if ("progress".equals(e.getPropertyName())) {
					progressBar.setIndeterminate(false);
					progressBar.setValue((Integer) e.getNewValue());
					System.out.println("**: " + e.getNewValue());
				}
			}
		});
		task.execute();
	}

	private class TwoWorker extends SwingWorker<Double, Double> {

		private static final int N = 734;
		private final DecimalFormat df = new DecimalFormat(s);

		@Override
		protected Double doInBackground() throws Exception {
			int cntr = 1; //
			double d1;
			double d2;
			double d3;
			for (int i = 0; i <=N; i++) {   
				d1 = N;
				d2 = i;
				d3 = d2/d1;
				d3 = d3 * 100;
				System.out.println(i + "++ " + d3);
				if(d3 >= cntr){
					System.out.println(i + "UPDATE");
					cntr++;
					setProgress(cntr);
					publish(Double.valueOf(cntr));
					Thread.sleep(250); // simulate latency
				}
			}
			return Double.valueOf(0);
		}

		@Override
		protected void process(List<Double> chunks) {
			for (double d : chunks) {
				label.setText(df.format(d));
				System.out.println(": " + d);
			}
		}
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				Threading_01 t = new Threading_01();
				t.runCalc();
			}
		});
	}
}
