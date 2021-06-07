import random

f = open("logs.txt", "w")

for i in range(200):
  r = random.random()
  if r <= 0.2:
    r2 = random.random()
    if r2 <= 0.4:
      f.write(f"Error del servidor en la operación {i}\n")
    else:
      f.write(f"Error en operación {i}\n")
  else:
    f.write(f"Operación {i} ejecutada\n")
