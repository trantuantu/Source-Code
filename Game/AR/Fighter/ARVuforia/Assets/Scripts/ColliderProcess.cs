using UnityEngine;
using System.Collections;

public class ColliderProcess : MonoBehaviour
{
    public int posType;
    private float flood = 327f;
    private Vector3 currentVector1;
    private Vector3 currentVector2;
    // Use this for initialization
    void Start()
    {
        currentVector1 = GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("Flood1").GetComponent<RectTransform>().localPosition;
        currentVector2 = GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("Flood2").GetComponent<RectTransform>().localPosition;
    }
    void OnParticleCollision(GameObject other)
    {
        if (posType == 1)
        {
            if (this.gameObject.transform.parent.parent.FindChild("Cyclone").gameObject.activeSelf == false)
            {
                flood -= 6;
                GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("Flood1").GetComponent<RectTransform>().sizeDelta = new Vector2(flood, 53);
                GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("Flood1").GetComponent<RectTransform>().localPosition = new Vector3(GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("Flood1").GetComponent<RectTransform>().localPosition.x + 3, GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("Flood1").GetComponent<RectTransform>().localPosition.y, 0);
            }
            if (flood <= 0)
            {
                GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("KO").GetComponent<UnityEngine.UI.Text>().text = GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("Character2").GetComponent<UnityEngine.UI.Text>().text + " WIN";
            }
        }else
        {
            if (this.gameObject.transform.parent.parent.FindChild("Cyclone").gameObject.activeSelf == false)
            {
                flood -= 6;
                GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("Flood2").GetComponent<RectTransform>().sizeDelta = new Vector2(flood, 53);
                GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("Flood2").GetComponent<RectTransform>().localPosition = new Vector3(GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("Flood2").GetComponent<RectTransform>().localPosition.x - 3, GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("Flood2").GetComponent<RectTransform>().localPosition.y, 0);
            }
            if (flood <= 0)
            {
                GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("KO").GetComponent<UnityEngine.UI.Text>().text = GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("Character1").GetComponent<UnityEngine.UI.Text>().text + " WIN";
            }    
           
        }
       
    }
    public void reset()
    {
        flood = 327f;
        GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("Flood1").GetComponent<RectTransform>().sizeDelta = new Vector2(flood, 53);
        GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("Flood2").GetComponent<RectTransform>().sizeDelta = new Vector2(flood, 53);
        GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("Flood1").GetComponent<RectTransform>().localPosition = currentVector1;
        GameObject.FindGameObjectWithTag("Canvas").transform.FindChild("Flood2").GetComponent<RectTransform>().localPosition = currentVector2;
    }
}
