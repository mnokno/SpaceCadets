using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace Spirograph
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        private float R;
        private float r;
        private float O;
        private float P = 1000;
        private float S = 100;
        private float offset = 180f;

        public MainWindow()
        {
            InitializeComponent();
        }

        public void PaintDot(float x, float y, int size = 1)
        {
            Ellipse ellipse = new Ellipse();
            ellipse.Fill = Brushes.Black;
            ellipse.Width = 1;
            ellipse.Height = 1;
            Canvas.SetTop(ellipse, y + offset);
            Canvas.SetLeft(ellipse, x + offset);
            canvas.Children.Add(ellipse);
        }

        public void PaintSpirographPoint(float R, float r, float O, float t)
        {
            float x = (R - r) * MathF.Cos(t) + O * MathF.Cos(((R-r)/r)*t);
            float y = (R - r) * MathF.Sin(t) + O * MathF.Sin(((R - r) / r) * t);
            PaintDot(x, y);
        }

        private void RePaintSpirograph()
        {
            canvas.Children.Clear();
            for (int i = 0; i < P; i++)
            {
                PaintSpirographPoint(R, r, O, i / S);
            }
        }

        private void txtBoxR_TextChanged(object sender, TextChangedEventArgs e)
        {
            if (float.TryParse(txtBoxR.Text, out R))
            {
                RePaintSpirograph();
            }
        }

        private void txtBoxr_TextChanged_1(object sender, TextChangedEventArgs e)
        {
            if (float.TryParse(txtBoxr.Text, out r))
            {
                RePaintSpirograph();
            }
        }

        private void txtBoxO_TextChanged(object sender, TextChangedEventArgs e)
        {
            if (float.TryParse(txtBoxO.Text, out O))
            {
                RePaintSpirograph();
            }
        }

        private void txtBoxS_TextChanged(object sender, TextChangedEventArgs e)
        {
            if (float.TryParse(txtBoxS.Text, out S))
            {
                RePaintSpirograph();
            }
        }

        private void txtBoxP_TextChanged(object sender, TextChangedEventArgs e)
        {
            if (float.TryParse(txtBoxP.Text, out P))
            {
                RePaintSpirograph();
            }
        }

        private void Grid_SizeChanged(object sender, SizeChangedEventArgs e)
        {
            offset = (float)canvas.Width / 2f;
        }


    }
}
