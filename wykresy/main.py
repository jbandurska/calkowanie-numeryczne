import pandas as pd
import matplotlib.pyplot as plt

df = pd.read_csv("results.csv")

df = df.set_index("liczbaPodzialow")
df[["bladTrapezow", "bladSimpsona", "bladCSI"]].plot(logy=True)
plt.show()
