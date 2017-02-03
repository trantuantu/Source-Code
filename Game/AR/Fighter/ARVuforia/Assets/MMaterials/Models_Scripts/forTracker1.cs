// based on this: https://developer.vuforia.com/forum/faq/unity-how-can-i-popup-gui-button-when-target-detected
// aso this: https://developer.vuforia.com/forum/faq/unity-how-can-i-play-audio-when-targets-get-detected
using UnityEngine;
using System.Collections;
using Vuforia;

public class forTracker1 : MonoBehaviour, ITrackableEventHandler {
	private TrackableBehaviour mTrackableBehaviour; // trackers
	
	private bool mShowGUIButton  = false; // GUI 
    private Rect attackButton = new Rect(0, Screen.width / 6, 1000, 1000); // GUI
    private Rect defenseButton = new Rect(0, Screen.width / 3 + Screen.width / 8, 1000, 1000); // GUI
    private GameObject Spark, Cyclone, Fireball, energy; //effects, monsters
    private int flag = 0;
    private bool isPlay = false;

    void Start()
	{
        Spark = transform.FindChild("Spark").gameObject; // effects
		Spark.SetActive(false); 	
		// effects
		Cyclone = transform.FindChild("Cyclone").gameObject; // effects
		Cyclone.SetActive(false);

        Fireball = transform.FindChild("Fireball").gameObject; // effects
        Fireball.SetActive(false);

        energy = transform.FindChild("energyBlast").gameObject; // effects
        energy.SetActive(false);

        mTrackableBehaviour = GetComponent<TrackableBehaviour>();
		if (mTrackableBehaviour)
		{
			mTrackableBehaviour.RegisterTrackableEventHandler(this);
		}
	}
	
	public void OnTrackableStateChanged(
		TrackableBehaviour.Status previousStatus,
		TrackableBehaviour.Status newStatus)
	{
		if (newStatus == TrackableBehaviour.Status.DETECTED ||
		    newStatus == TrackableBehaviour.Status.TRACKED ||
		    newStatus == TrackableBehaviour.Status.EXTENDED_TRACKED)
		{

            mShowGUIButton = true;
            Cyclone.SetActive(true);
            energy.SetActive(true);
            isPlay = false;
            this.gameObject.GetComponents<AudioSource>()[1].Play();
            if (GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("Character1").GetComponent<UnityEngine.UI.Text>().text == "")
            {
                attackButton = new Rect(7 * Screen.width / 8, Screen.height / 4, Screen.width / 7, Screen.height / 8); // GUI
                defenseButton = new Rect(7 * Screen.width / 8, Screen.height / 4 + Screen.height / 8, Screen.width / 7, Screen.height / 8); // GUI

                GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("Flood1").GetComponent<RectTransform>().sizeDelta = new Vector2(327f, 53);
                GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("Flood1").GetComponent<RectTransform>().localPosition = new Vector3(236.5f, GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("Flood1").GetComponent<RectTransform>().localPosition.y, 0);
                GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("KO").GetComponent<UnityEngine.UI.Text>().text = "";
                GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("BF1").GetComponent<UnityEngine.UI.Image>().enabled = true;
                GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("Flood1").GetComponent<UnityEngine.UI.Image>().enabled = true;
                GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("Character1").GetComponent<UnityEngine.UI.Text>().text = "DRAGON";
                transform.FindChild("BEWD").FindChild("Regroup29").GetComponent<ColliderProcess>().posType = 1;
                transform.FindChild("BEWD").FindChild("Regroup29").GetComponent<ColliderProcess>().reset();
                flag = 1;
            }
            else
            {
                attackButton = new Rect(0, Screen.height / 4, Screen.width / 7, Screen.height / 8); // GUI
                defenseButton = new Rect(0, Screen.height / 4 + Screen.height / 8, Screen.width / 7, Screen.height / 8);
                GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("Flood2").GetComponent<RectTransform>().sizeDelta = new Vector2(327f, 53);
                GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("Flood2").GetComponent<RectTransform>().localPosition = new Vector3(-236.5f, GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("Flood1").GetComponent<RectTransform>().localPosition.y, 0);
                GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("KO").GetComponent<UnityEngine.UI.Text>().text = "";
                GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("BF2").GetComponent<UnityEngine.UI.Image>().enabled = true;
                GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("Flood2").GetComponent<UnityEngine.UI.Image>().enabled = true;
                GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("Character2").GetComponent<UnityEngine.UI.Text>().text = "DRAGON";
                transform.FindChild("BEWD").FindChild("Regroup29").GetComponent<ColliderProcess>().posType = 2;
                transform.FindChild("BEWD").FindChild("Regroup29").GetComponent<ColliderProcess>().reset();
                flag = 2;
            }
          
            
            StartCoroutine(StartWait(10F));
        }
		else
		{
            energy.SetActive(false);
            if (flag == 1)
            {
                GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("BF1").GetComponent<UnityEngine.UI.Image>().enabled = false;
                GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("Flood1").GetComponent<UnityEngine.UI.Image>().enabled = false;
                GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("Character1").GetComponent<UnityEngine.UI.Text>().text = "";
            }else
            {
                GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("BF2").GetComponent<UnityEngine.UI.Image>().enabled = false;
                GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("Flood2").GetComponent<UnityEngine.UI.Image>().enabled = false;
                GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("Character2").GetComponent<UnityEngine.UI.Text>().text = "";
            }
            mShowGUIButton  = false;
            isPlay = true;
        }
	}  
	void OnGUI() {


        if (mShowGUIButton && GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("RawImage").gameObject.activeSelf == false) {
			// draw the GUI button
			if (GUI.Button(attackButton, "ATK/3000")) {
				Debug.Log ("Attack!");
				Cyclone.SetActive(false);
				Spark.SetActive(true);
                Fireball.SetActive(true);
                this.gameObject.GetComponents<AudioSource>()[0].Play();
                StartCoroutine(StartWait(6F));
				// do something on button click 
			}
			if (GUI.Button(defenseButton, "DEF/2500")) {
				Debug.Log ("Defense!");
				Cyclone.SetActive(true);
                this.gameObject.GetComponents<AudioSource>()[1].Play();
                StartCoroutine(StartWait(6F));
                //Spark.SetActive(false);
            }
		}
        if (isPlay == false && GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("RawImage").gameObject.activeSelf == true) isPlay = true;
        if (GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("KO").GetComponent<UnityEngine.UI.Text>().text != "") mShowGUIButton = false;
    }

	IEnumerator StartWait(float time) 
	{
		yield return StartCoroutine(Wait(time));
		Spark.SetActive(false);
		Cyclone.SetActive(false);
        Fireball.SetActive(false);
        this.gameObject.GetComponents<AudioSource>()[0].Stop();
        this.gameObject.GetComponents<AudioSource>()[1].Stop();
        if (isPlay == false && GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("Character1").GetComponent<UnityEngine.UI.Text>().text != "" && GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("Character2").GetComponent<UnityEngine.UI.Text>().text != "")
        {
            mShowGUIButton = false;
            GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("RawImage").gameObject.SetActive(true);
            GameObject.FindGameObjectWithTag("ARCam").GetComponent<AudioSource>().Stop();
            GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("RawImage").GetComponent<PlayVideo>().Play();
            StartCoroutine(StartWait1(29F));
            //mShowGUIButton = true;
            isPlay = true;
        }
    }
    IEnumerator StartWait1(float time)
    {
        yield return StartCoroutine(Wait(time));
        GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("RawImage").GetComponent<PlayVideo>().Stop();
        GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("RawImage").gameObject.SetActive(false);
        GameObject.FindGameObjectWithTag("ARCam").GetComponent<AudioSource>().Play();
        mShowGUIButton = true;
    }

    IEnumerator Wait(float seconds)
	{
		yield return new WaitForSeconds(seconds);
	}

}


