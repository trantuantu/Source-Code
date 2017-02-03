using UnityEngine;
using System.Collections;
using UnityEngine.UI;
[RequireComponent(typeof(AudioSource))]
public class Trailer : MonoBehaviour
{
    public MovieTexture movie;
    private AudioSource audio;

    // Use this for initialization
    void Start()
    {
        GetComponent<RawImage>().texture = movie as MovieTexture;
        audio = GetComponent<AudioSource>();
        movie.Play();
        audio.Play();
        GameObject.FindGameObjectWithTag("ARCam").GetComponent<AudioSource>().Stop();
        StartCoroutine(StartWait(31F));
    }
    IEnumerator StartWait(float time)
    {
        yield return StartCoroutine(Wait(time));
        this.gameObject.SetActive(false);
        GameObject.FindGameObjectWithTag("ARCam").GetComponent<AudioSource>().Play();
    }
    IEnumerator Wait(float seconds)
    {
        yield return new WaitForSeconds(seconds);
    }
}
