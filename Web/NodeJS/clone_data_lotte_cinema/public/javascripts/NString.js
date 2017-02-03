var NString = {
    normalString: function(s)
    {
      var temp = s;
      for (var i = 0; i < temp.length; i++)
      {
        if (temp.charAt(i) != " ")
        {
          temp = temp.substr(i);
          break;
        }
      }
      return temp;
    },
    setCharAt: function(str,index,chr) {
      if(index > str.length-1) return str;
      return str.substr(0,index) + chr + str.substr(index+1);
    },
    normalDate: function(s)
    {
      var temp = s;
      var res = "";
      var sIndex;
      for (var i = 0; i < temp.length; i++)
      {
        if (!(temp.charAt(i) == "\r" || temp.charAt(i) == "\n" || temp.charAt(i) == " "))
        {
            temp = temp.substr(i);
            sIndex = i;
            break;
        }
       
      }
       for (var i = 0; i < temp.length; i++)
        {
          if (temp.charAt(i) != "\r")
            {
                res += temp.charAt(i);
            }else break;
        }
        return res;
      }
}
module.exports = NString;
