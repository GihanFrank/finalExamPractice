//******************************************************************************
// Total2d.java:	Applet
//
//******************************************************************************
import java.applet.*;
import java.awt.*;
import java.awt.image.*;

//==============================================================================
// Main Class for applet Total2d
class Main{
	public static void main(String[] args) {
		
	}
}
//
//==============================================================================
public class Total2d extends Applet implements Runnable
{
	// THREAD SUPPORT:
	//		m_Total2d	is the Thread object for the applet
	//--------------------------------------------------------------------------
	Thread	 m_Total2d = null;

	// PARAMETER SUPPORT:
	//		Parameters allow an HTML author to pass information to the applet;
	// the HTML author specifies them using the <PARAM> tag within the <APPLET>
	// tag.  The following variables are used to store the values of the
	// parameters.
    //--------------------------------------------------------------------------

    // Members for applet parameters
    // <type>       <MemberVar>    = <Default Value>
    //--------------------------------------------------------------------------
	private int m_cawidth = 400;
	private int m_caheight = 400;
	private int dialogy = 280; // offset for dialog box
	private int xscale = 8;		// scale factors
	private int yscale = 8;

	// Local variables
	private Panel mypanel;			// Panel for Dialog Box
	private totalgui mygui;			// Dialog box GUI
	private Label mylabel;			// opening caption
	private IndexColorModel icm;	// index color model
	private byte[][] paletteTable;	// Palette table
	private Image img;				// Image for display
	private byte[] caarray;  		// CA data
	private byte[] newdata;			// to store next step
	private int width;				// width of CA
	private int height;				// height of CA
	private byte[] neighbors;		// neighbor filter
	private byte[] death;			// transition rules for death
	private byte[] birth;			// transition rules for births



    // Parameter names.  To change a name of a parameter, you need only make
	// a single change.  Simply modify the value of the parameter string below.
    //--------------------------------------------------------------------------
	private final String PARAM_cawidth = "cawidth";
	private final String PARAM_caheight = "caheight";

	// Total2d Class Constructor
	//--------------------------------------------------------------------------
	public Total2d()
	{
		// TODO: Add constructor code here
	}

	// APPLET INFO SUPPORT:
	//		The getAppletInfo() method returns a string describing the applet's
	// author, copyright date, or miscellaneous information.
    //--------------------------------------------------------------------------
	public String getAppletInfo()
	{
		return "Name: 2D totalistic Cellular Automata\r\n" +
		       "Author: Hector Yee, yhy1@cornell.edu\r\n" +
		       "Date: 18th October 1996\r\n" +
		       "Created with Microsoft Visual J++ Version 1.0";
	}

	// PARAMETER SUPPORT
	//		The getParameterInfo() method returns an array of strings describing
	// the parameters understood by this applet.
	//
    // Total2d Parameter Information:
    //  { "Name", "Type", "Description" },
    //--------------------------------------------------------------------------
	public String[][] getParameterInfo()
	{
		String[][] info =
		{
			{ PARAM_cawidth, "int", "Width of image" },
			{ PARAM_caheight, "int", "height of image" },
		};
		return info;		
	}

	// The init() method is called by the AWT when an applet is first loaded or
	// reloaded.  Override this method to perform whatever initialization your
	// applet needs, such as initializing data structures, loading images or
	// fonts, creating frame windows, setting the layout manager, or adding UI
	// components.
    //--------------------------------------------------------------------------
	public void init()
	{
		int i,j;
		// PARAMETER SUPPORT
		//		The following code retrieves the value of each parameter
		// specified with the <PARAM> tag and stores it in a member
		// variable.
		//----------------------------------------------------------------------
		String param;

		// cawidth: Width of image
		//----------------------------------------------------------------------
		param = getParameter(PARAM_cawidth);
		if (param != null)
			m_cawidth = Integer.parseInt(param);

		// caheight: height of image
		//----------------------------------------------------------------------
		param = getParameter(PARAM_caheight);
		if (param != null)
			m_caheight = Integer.parseInt(param);

        // If you use a ResourceWizard-generated "control creator" class to
        // arrange controls in your applet, you may want to call its
        // CreateControls() method from within this method. Remove the following
        // call to resize() before adding the call to CreateControls();
        // CreateControls() does its own resizing.
        //----------------------------------------------------------------------
		if (m_caheight<dialogy) { m_caheight=dialogy+200; };

		resize(m_cawidth, m_caheight);

		// TODO: Place additional initialization code here
		mylabel= new Label("2D Totalistic CA in Java by Hector Yee (yhy1@cornell.edu)");
		add(mylabel);
		mypanel = new Panel();
		add(mypanel);
        mygui = new totalgui(mypanel);
		mygui.CreateControls();

		// init variables
        neighbors = new byte[9];
		for (i=0; i<9; i++)
			{ neighbors[i]=0; };

		death = new byte[10];
		for (i=0; i<10; i++)
			{ death[i]=0; };

		birth = new byte[10];
		for (i=0; i<10; i++)
			{ birth[i]=0; };



		paletteTable = new byte[3][2];
		MakePalette();
		width=(m_cawidth-2)/xscale;
		height=(m_caheight-dialogy-2)/yscale;

		// randomize array
		caarray = new byte[width*height];
		newdata = new byte[width*height];
		for (i=0; i<width; i++)
		{
			for (j=0; j<height; j++)
			{
			  caarray[i+j*width]=(byte) (Math.random()>0.5?0:1);
			};
		};

		// add image

		img=createImage(new MemoryImageSource(width,height,icm,caarray,0,width));


	}

	// Place additional applet clean up code here.  destroy() is called when
	// when you applet is terminating and being unloaded.
	//-------------------------------------------------------------------------
	public void destroy()
	{
		// TODO: Place applet cleanup code here
	}

	// Total2d Paint Handler
	//--------------------------------------------------------------------------
	public void paint(Graphics g)
	{
		// TODO: Place applet paint code here
		img.flush();
		g.drawImage(img, xscale,yscale+ dialogy, (width-1)*xscale, (height-1)*yscale, null);
	}

	//		The start() method is called when the page containing the applet
	// first appears on the screen. The AppletWizard's initial implementation
	// of this method starts execution of the applet's thread.
	//--------------------------------------------------------------------------
	public void start()
	{
		if (m_Total2d == null)
		{
			m_Total2d = new Thread(this);
			m_Total2d.start();
			m_Total2d.setPriority(Thread.MAX_PRIORITY);
		}
		// TODO: Place additional applet start code here
	}
	
	//		The stop() method is called when the page containing the applet is
	// no longer on the screen. The AppletWizard's initial implementation of
	// this method stops execution of the applet's thread.
	//--------------------------------------------------------------------------
	public void stop()
	{
		if (m_Total2d != null)
		{
			m_Total2d.stop();
			m_Total2d = null;
		}

		// TODO: Place additional applet stop code here
	}

	// THREAD SUPPORT
	//		The run() method is called when the applet's thread is started. If
	// your applet performs any ongoing activities without waiting for user
	// input, the code for implementing that behavior typically goes here. For
	// example, for an applet that performs animation, the run() method controls
	// the display of images.
	//--------------------------------------------------------------------------
	public void run()
	{
		while (true)
		{
			try
			{
			if (mygui.IDC_BUTTON1.getLabel().equals("Stop"))
			{
				iterate();
			};

				repaint();
				// TODO:  Add additional thread-specific code here
				Thread.sleep(10);
			}
			catch (InterruptedException e)
			{
				// TODO: Place exception-handling code here in case an
				//       InterruptedException is thrown by Thread.sleep(),
				//		 meaning that another thread has interrupted this one
				stop();
			}
		}
	}

	// MOUSE SUPPORT:
	//		The mouseDown() method is called if the mouse button is pressed
	// while the mouse cursor is over the applet's portion of the screen.
	//--------------------------------------------------------------------------
	public boolean mouseDown(Event evt, int x, int y)
	{
		// TODO: Place applet mouseDown code here

		// flip the CA whenever the mouse is clicked over the CA
		int mx, my,d;

		mx = (x-xscale)/xscale;
		my = (y - yscale - dialogy)/yscale;
		
		d = mx + width*my;
		
		if ((d>=0) && (d< width*height))
		{
			caarray[d]=(caarray[d]==0?(byte)1:(byte)0);
		};

		repaint();

		return true;
	}

	// MOUSE SUPPORT:
	//		The mouseUp() method is called if the mouse button is released
	// while the mouse cursor is over the applet's portion of the screen.
	//--------------------------------------------------------------------------
	public boolean mouseUp(Event evt, int x, int y)
	{
		// TODO: Place applet mouseUp code here
		return true;
	}


	// TODO: Place additional applet code here

	// this method creates the palette for the CA
	void MakePalette() {
		// color zero is green
        paletteTable[1][0]=(byte) 255;
        paletteTable[2][0]=(byte) 0;
        paletteTable[0][0]=(byte) 0;
		// color one is blue
        paletteTable[1][1]=(byte) 0;
        paletteTable[2][1]=(byte) 255;
        paletteTable[0][1]=(byte) 0;
     
		icm = new IndexColorModel(1, 2, paletteTable[0], paletteTable[1], paletteTable[2]);
	} // make palette

	// this method redraws the CA
	public void update(Graphics g) {
        img.flush();
		g.drawImage(img, xscale, yscale+dialogy, (width-1)*xscale, (height-1)*yscale, null);		
	} // update

	// randomize CA state
	public void randomizeCA()
	{
		int i,j;

		for (i=0; i<width; i++)
		{
			for (j=0; j<height; j++)
			{
			  caarray[i+j*width]=(byte) (Math.random()>0.5?0:1);
			};
		};
	};

	// reset CA to clear
	public void clearCA()
	{
		int i,j;

		for (i=0; i<width; i++)
		{
			for (j=0; j<height; j++)
			{
			  caarray[i+j*width]=(byte) 0;
			};
		};
	};

	// do settings for Conway's game of life
	public void setGameofLife()
	{
		int i;

		// set birth/death rules
		for (i=0; i<10; i++)
		{
			birth[i]=(byte) 0;
			death[i]=(byte) 1;
		};
		birth[3]=(byte) 1;
		death[2]=(byte) 0;
		death[3]=(byte) 0;

		// set neighbors
		for (i=0; i<9; i++)
		{
			neighbors[i]=1;
		};				   
		neighbors[4]=0;

		// update controls

		// neighbors are 1-9
		// deaths are 10-19
		// births are 20-29

		// neighborhood
		mygui.IDC_CHECK1.setState(true);
		mygui.IDC_CHECK2.setState(true);
		mygui.IDC_CHECK3.setState(true);
		mygui.IDC_CHECK4.setState(true);
		mygui.IDC_CHECK5.setState(false);
		mygui.IDC_CHECK6.setState(true);
		mygui.IDC_CHECK7.setState(true);
		mygui.IDC_CHECK8.setState(true);
		mygui.IDC_CHECK9.setState(true);

		// deaths
		mygui.IDC_CHECK10.setState(true);
		mygui.IDC_CHECK11.setState(true);
		mygui.IDC_CHECK12.setState(false);
		mygui.IDC_CHECK13.setState(false);
		mygui.IDC_CHECK14.setState(true);
		mygui.IDC_CHECK15.setState(true);
		mygui.IDC_CHECK16.setState(true);
		mygui.IDC_CHECK17.setState(true);
		mygui.IDC_CHECK18.setState(true);
		mygui.IDC_CHECK19.setState(true);

		// births
		mygui.IDC_CHECK20.setState(false);
		mygui.IDC_CHECK21.setState(false);
		mygui.IDC_CHECK22.setState(false);
		mygui.IDC_CHECK23.setState(true);
		mygui.IDC_CHECK24.setState(false);
		mygui.IDC_CHECK25.setState(false);
		mygui.IDC_CHECK26.setState(false);
		mygui.IDC_CHECK27.setState(false);
		mygui.IDC_CHECK28.setState(false);
		mygui.IDC_CHECK29.setState(false);

				
	};

	// returns x coordinate given array index i
	// and neighbor number j
	public int getCAx(int i, int j)
	{
		int offs; // x offset
		int x;	  // calculated x value

		offs= (j % 3) -1;

		x = (i % width) + offs;

		if (x<0) { return width-1; };
		if (x>=width) { return 0; };
		return x;		
	};

	// returns y coordinate given array index i
	// and neighbor number j
	public int getCAy(int i, int j)
	{
		int offs;  // y offset
		int y;	   // calculated y value

		offs=(j/3)-1;

		y = (i / width) + offs;

		if (y<0) { return height-1; };
		if (y>=height) { return 0; };
		return y;
	};

	// returns cell neighbor given array index i
	// and neighbor number j

	public int getneighbor(int i, int j)
	{
		int x,y;

		x=getCAx(i,j);
		y=getCAy(i,j);
		return ((int) caarray[x+y*width]);
	};

	// simulate the ca
	public void iterate()
	{
		int num;	// neighbors
		int i,j;	// loop variables
		int limit;  // loop limit

		limit = width*height;

		for (i=0; i<limit; i++)
		{
			num=0;
			// calculate number of neighbors

			for (j=0; j<9; j++)
			{
				if (neighbors[j]==1)
				{
					num=num+ getneighbor(i,j);
				};
			};

			// decide on survival
			if (death[num]==1)
			{
				newdata[i]=0;				
			}
			else
			{
				newdata[i]=caarray[i];
			};

			// decide on birth
			if (birth[num]==1)
			{
				newdata[i]= (byte) 1;
			};
		}; // loop i
		for (i=0; i<limit; i++)
		{
			caarray[i]=newdata[i];
		};
	}; // of iterate
	
	// this is the event handler
	public boolean action(Event evt, Object  what)
	{
		int i;
		Object target = evt.target;

		// handle buttons

		// start/stop button
		if (target == mygui.IDC_BUTTON1)
		{
			if (mygui.IDC_BUTTON1.getLabel()=="Start")
			// ca is not running , so start it
			{
				mygui.IDC_BUTTON1.setLabel("Stop");
				repaint();
			}
			else
			// stop the ca
			{
				mygui.IDC_BUTTON1.setLabel("Start");
				repaint();
			}; // start/stop button event
			return true;
		};

		// handle randomize CA
		if (target == mygui.IDC_BUTTON2)
		{
			this.randomizeCA();
			return true;
		};

		// set settings for game of life
		if (target == mygui.IDC_BUTTON3)
		{
			this.setGameofLife();
			return true;
		};

		// clear the CA
		if (target == mygui.IDC_BUTTON4)
		{
			this.clearCA();
			return true;
		};

		// handle neighbor filters
		if (target == mygui.IDC_CHECK1)
		{
			neighbors[0]=(mygui.IDC_CHECK1.getState()?(byte)1:(byte)0);
			return true;
		};
		if (target == mygui.IDC_CHECK2)
		{
			neighbors[1]=(mygui.IDC_CHECK2.getState()?(byte)1:(byte)0);
			return true;
		};
		if (target == mygui.IDC_CHECK3)
		{
			neighbors[2]=(mygui.IDC_CHECK3.getState()?(byte)1:(byte)0);
			return true;
		};
		if (target == mygui.IDC_CHECK4)
		{
			neighbors[3]=(mygui.IDC_CHECK4.getState()?(byte)1:(byte)0);
			return true;
		};
		if (target == mygui.IDC_CHECK5)
		{
			neighbors[4]=(mygui.IDC_CHECK5.getState()?(byte)1:(byte)0);
			return true;
		};
		if (target == mygui.IDC_CHECK6)
		{
			neighbors[5]=(mygui.IDC_CHECK6.getState()?(byte)1:(byte)0);
			return true;
		};
		if (target == mygui.IDC_CHECK7)
		{
			neighbors[6]=(mygui.IDC_CHECK7.getState()?(byte)1:(byte)0);
			return true;
		};
		if (target == mygui.IDC_CHECK8)
		{
			neighbors[7]=(mygui.IDC_CHECK8.getState()?(byte)1:(byte)0);
			return true;
		};
		if (target == mygui.IDC_CHECK9)
		{
			neighbors[8]=(mygui.IDC_CHECK9.getState()?(byte)1:(byte)0);
			return true;
		};

		// handle transition table for deaths

		if (target == mygui.IDC_CHECK10)
		{
			death[0]=(mygui.IDC_CHECK10.getState()?(byte)1:(byte)0);
			return true;
		};
		if (target == mygui.IDC_CHECK11)
		{
			death[1]=(mygui.IDC_CHECK11.getState()?(byte)1:(byte)0);
			return true;
		};
		if (target == mygui.IDC_CHECK12)
		{
			death[2]=(mygui.IDC_CHECK12.getState()?(byte)1:(byte)0);
			return true;
		};
		if (target == mygui.IDC_CHECK13)
		{
			death[3]=(mygui.IDC_CHECK13.getState()?(byte)1:(byte)0);
			return true;
		};
		if (target == mygui.IDC_CHECK14)
		{
			death[4]=(mygui.IDC_CHECK14.getState()?(byte)1:(byte)0);
			return true;
		};
		if (target == mygui.IDC_CHECK15)
		{
			death[5]=(mygui.IDC_CHECK15.getState()?(byte)1:(byte)0);
			return true;
		};
		if (target == mygui.IDC_CHECK16)
		{
			death[6]=(mygui.IDC_CHECK16.getState()?(byte)1:(byte)0);
			return true;
		};
		if (target == mygui.IDC_CHECK17)
		{
			death[7]=(mygui.IDC_CHECK17.getState()?(byte)1:(byte)0);
			return true;
		};
		if (target == mygui.IDC_CHECK18)
		{
			death[8]=(mygui.IDC_CHECK18.getState()?(byte)1:(byte)0);
			return true;
		};
		if (target == mygui.IDC_CHECK19)
		{
			death[9]=(mygui.IDC_CHECK19.getState()?(byte)1:(byte)0);
			return true;
		};

		// handle transition table for births

		if (target == mygui.IDC_CHECK20)
		{
			birth[0]=(mygui.IDC_CHECK20.getState()?(byte)1:(byte)0);
			return true;
		};
		if (target == mygui.IDC_CHECK21)
		{
			birth[1]=(mygui.IDC_CHECK21.getState()?(byte)1:(byte)0);
			return true;
		};
		if (target == mygui.IDC_CHECK22)
		{
			birth[2]=(mygui.IDC_CHECK22.getState()?(byte)1:(byte)0);
			return true;
		};
		if (target == mygui.IDC_CHECK23)
		{
			birth[3]=(mygui.IDC_CHECK23.getState()?(byte)1:(byte)0);
			return true;
		};
		if (target == mygui.IDC_CHECK24)
		{
			birth[4]=(mygui.IDC_CHECK24.getState()?(byte)1:(byte)0);
			return true;
		};
		if (target == mygui.IDC_CHECK25)
		{
			birth[5]=(mygui.IDC_CHECK25.getState()?(byte)1:(byte)0);
			return true;
		};
		if (target == mygui.IDC_CHECK26)
		{
			birth[6]=(mygui.IDC_CHECK26.getState()?(byte)1:(byte)0);
			return true;
		};
		if (target == mygui.IDC_CHECK27)
		{
			birth[7]=(mygui.IDC_CHECK27.getState()?(byte)1:(byte)0);
			return true;
		};
		if (target == mygui.IDC_CHECK28)
		{
			birth[8]=(mygui.IDC_CHECK28.getState()?(byte)1:(byte)0);
			return true;
		};
		if (target == mygui.IDC_CHECK29)
		{
			birth[9]=(mygui.IDC_CHECK29.getState()?(byte)1:(byte)0);
			return true;
		};

		// no handler for this event

	 	return false;
  } // event handler


}