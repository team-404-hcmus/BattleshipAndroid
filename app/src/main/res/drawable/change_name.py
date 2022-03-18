import os
import fnmatch

dir = os.listdir()
filtered = fnmatch.filter(dir,"paper_*.jpg")
for idx,value in enumerate(filtered):
    print("renaming: ",value)
    os.rename(value,"paper_"+str(idx)+".jpg")
