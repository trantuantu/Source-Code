// based on this: https://developer.vuforia.com/forum/faq/unity-how-can-i-popup-gui-button-when-target-detected
// aso this: https://developer.vuforia.com/forum/faq/unity-how-can-i-play-audio-when-targets-get-detected
using UnityEngine;
using System.Collections;
using Vuforia;

public class forTracker4 : MonoBehaviour, ITrackableEventHandler
{
    private TrackableBehaviour mTrackableBehaviour; // trackers
                                                    //float PlayerLifePoints = 4000f;
                                                    //public GUIStyle MyGUIstyle;
    private bool mShowGUIButton = false; // GUI 
    private Rect attackButton2 = new Rect(30, 30, Screen.height / 4, 40); // GUI
    private Rect defenseButton2 = new Rect(30, 80, Screen.height / 4, 40); // GUI
    private GameObject Landmime, MagmaBurst; //effects, monsters
    private int flag = 0;
    private bool isPlay = false;

    void Start()
    {
        Landmime = transform.FindChild("Cyclone").gameObject; // effects
        Landmime.SetActive(false);

        MagmaBurst = transform.FindChild("MagmaBurst").gameObject; // effects
        MagmaBurst.SetActive(false);

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
            Landmime.SetActive(true);
            this.gameObject.GetComponents<AudioSource>()[1].Play();
            mShowGUIButton = true;
            isPlay = false;

            if (GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("Character2").GetComponent<UnityEngine.UI.Text>().text == "")
            {
                attackButton2 = new Rect(0, Screen.height / 4, Screen.width / 7, Screen.height / 8); // GUI
                defenseButton2 = new Rect(0, Screen.height / 4 + Screen.height / 8, Screen.width / 7, Screen.height / 8);
                GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("Flood2").GetComponent<RectTransform>().sizeDelta = new Vector2(327f, 53);
                GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("Flood2").GetComponent<RectTransform>().localPosition = new Vector3(-236.5f, GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("Flood2").GetComponent<RectTransform>().localPosition.y, 0);
                GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("KO").GetComponent<UnityEngine.UI.Text>().text = "";
                GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("BF2").GetComponent<UnityEngine.UI.Image>().enabled = true;
                GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("Flood2").GetComponent<UnityEngine.UI.Image>().enabled = true;
                GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("Character2").GetComponent<UnityEngine.UI.Text>().text = "SPIDER";
                transform.FindChild("SPIDER").FindChild("Armature").GetComponent<ColliderProcess>().posType = 2;
                transform.FindChild("SPIDER").FindChild("Armature").GetComponent<ColliderProcess>().reset();
                flag = 2;
            }
            else
            {
                attackButton2 = new Rect(7 * Screen.width / 8, Screen.height / 4, Screen.width / 7, Screen.height / 8); // GUI
                defenseButton2 = new Rect(7 * Screen.width / 8, Screen.height / 4 + Screen.height / 8, Screen.width / 7, Screen.height / 8); // GUI
                GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("Flood1").GetComponent<RectTransform>().sizeDelta = new Vector2(327f, 53);
                GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("Flood1").GetComponent<RectTransform>().localPosition = new Vector3(236.5f, GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("Flood2").GetComponent<RectTransform>().localPosition.y, 0);
                GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("KO").GetComponent<UnityEngine.UI.Text>().text = "";
                GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("BF1").GetComponent<UnityEngine.UI.Image>().enabled = true;
                GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("Flood1").GetComponent<UnityEngine.UI.Image>().enabled = true;
                GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("Character1").GetComponent<UnityEngine.UI.Text>().text = "SPIDER";
                transform.FindChild("SPIDER").FindChild("Armature").GetComponent<ColliderProcess>().posType = 1;
                transform.FindChild("SPIDER").FindChild("Armature").GetComponent<ColliderProcess>().reset();
                flag = 1;
            }
            StartCoroutine(StartWait(8F));
        }
        else
        {
            Landmime.SetActive(false);
            isPlay = true;
            if (flag == 2)
            {
                GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("BF2").GetComponent<UnityEngine.UI.Image>().enabled = false;
                GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("Flood2").GetComponent<UnityEngine.UI.Image>().enabled = false;
                GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("Character2").GetComponent<UnityEngine.UI.Text>().text = "";
            }
            else
            {
                GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("BF1").GetComponent<UnityEngine.UI.Image>().enabled = false;
                GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("Flood1").GetComponent<UnityEngine.UI.Image>().enabled = false;
                GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("Character1").GetComponent<UnityEngine.UI.Text>().text = "";
            }
            mShowGUIButton = false;
            //defenseButton = false;
            //audio.Stop();
        }
    }

    void OnGUI()
    {
        if (mShowGUIButton && GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("RawImage").gameObject.activeSelf == false)
        {
            // draw the GUI button
            if (GUI.Button(attackButton2, "ATK/300"))
            {
                Debug.Log("Attack!");
                Landmime.SetActive(false);
                MagmaBurst.SetActive(true);
                this.gameObject.GetComponents<AudioSource>()[0].Play();
                transform.FindChild("SPIDER").gameObject.GetComponent<Animation>().Play();
                //this.gameObject.GetComponents<AudioSource>()[0].Play();
                StartCoroutine(StartWait(3F));


                // do something on button click 
            }
            if (GUI.Button(defenseButton2, "DEF/200"))
            {
                Debug.Log("Defense!");
                this.gameObject.GetComponents<AudioSource>()[1].Play();
                transform.FindChild("SPIDER").gameObject.GetComponent<Animation>().Play("Run");
                Landmime.SetActive(true);
                //this.gameObject.GetComponents<AudioSource>()[1].Play();
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
        Landmime.SetActive(false);
        MagmaBurst.SetActive(false);
        this.gameObject.GetComponents<AudioSource>()[0].Stop();
        this.gameObject.GetComponents<AudioSource>()[1].Stop();
        if (isPlay == false && GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("Character1").GetComponent<UnityEngine.UI.Text>().text != "" && GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("Character2").GetComponent<UnityEngine.UI.Text>().text != "")
        {
            mShowGUIButton = false;
            GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("RawImage").gameObject.SetActive(true);
            GameObject.FindGameObjectWithTag("ARCam").GetComponent<AudioSource>().Stop();
            GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("RawImage").GetComponent<PlayVideo>().Play();
            StartCoroutine(StartWait1(29F));
           
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


