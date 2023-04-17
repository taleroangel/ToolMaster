import { Brand } from "./brand"
import { City } from "./city"

export interface Tool {
  id: number
  name: string
  brand: Brand
  description: string
  image: string
  price: number
  cities: City[]
  units: number
}
