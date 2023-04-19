import { City } from "./city"

export interface User {
  id: number
  name: string
  lastName: string
  birthDate: string
  city: City
  active: boolean
  username: string
}
