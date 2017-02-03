using UnityEngine;
using System.Collections;
using UnityEngine.UI;
[RequireComponent(typeof(AudioSource))]
public class PlayVideo : MonoBehaviour {
    public MovieTexture movie;
    private AudioSource audio;

    // Use this for initialization
    void Start()
    {
        GetComponent<RawImage>().texture = movie as MovieTexture;
        audio = GetComponent<AudioSource>();
        this.gameObject.SetActive(false);
    }
    public void Play()
    {
        movie.Play();
        audio.Play();
    }
    public void Stop()
    {
        movie.Stop();
        audio.Stop();
    }
}
